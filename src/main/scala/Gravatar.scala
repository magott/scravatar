package scravatar

import java.net.{URL, URLEncoder}

/**
 * Immutable (thread safe) class used to generate Gravatar URLs
 * @author Morten Andersen-Gott - code@andersen-gott.com
 */
case class Gravatar(private val emailAddress:String, ssl:Boolean, forceDefault:Boolean, defaultImage:Option[DefaultImage], rating:Option[Rating], size:Option[Int]) {

  if(! size.forall(isValidSize))
    throw new IllegalArgumentException("Size must be positive and cannot exceed 2048")

  val email = emailAddress.trim.toLowerCase
  val emailHash = Md5.hash(email)

  def ssl(ssl:Boolean):Gravatar = copy(ssl=ssl)
  def default(default:DefaultImage):Gravatar = copy(defaultImage = Some(default))
  def forceDefault(forceDefault:Boolean):Gravatar = copy(forceDefault = forceDefault)
  def maxRatedAs(rating:Rating):Gravatar = copy(rating = Some(rating))
  def size(size:Int):Gravatar = copy(size = Some(size))

  /**
   * Builds the Gravatar url
   * @return gravatar url as String
   */
  def avatarUrl:String = {
    initUriBuilder.segments("avatar",emailHash)
      .queryParam("d",defaultImage.map(_.value))
      .queryParam("r", rating.map(_.value))
      .queryParam("s", size.map(_.toString))
    .build.toString
  }

  def downloadImage:Array[Byte] = {
    val is = new URL(avatarUrl).openStream
    Stream.continually(is.read).takeWhile(-1 !=).map(_.toByte).toArray
  }

  private def initUriBuilder:URIBuilder = {
    val gravatarBase = "www.gravatar.com"
    val gravatarSslBase = "secure.gravatar.com"
    val urlBuilder =
      if(ssl) URIBuilder.empty.withHost(gravatarSslBase).withScheme("https")
      else URIBuilder.empty.withHost(gravatarBase).withScheme("http")
    if(forceDefault)
      urlBuilder.queryParam("forcedefault","y")
    else urlBuilder
  }

  private def isValidSize(size:Int) = size > 0 && size <= 2048

}

object Gravatar{
  def apply(email:String):Gravatar = Gravatar(email, false, false, None, None, None)
}

case class DefaultImage(value:String)
case object Monster extends DefaultImage("monsterid")
case object MysteryMan extends DefaultImage("mm")
case object IdentIcon extends DefaultImage("identicon")
case object Wavatar extends DefaultImage("wavatar")
case object Retro extends DefaultImage("retro")
case object FourOFour extends DefaultImage("404")
case class CustomImage(url:String) extends DefaultImage(URLEncoder.encode(url, "UTF-8"))

case class Rating(value:String)
case object G extends Rating("g")
case object PG extends Rating("pg")
case object R extends Rating("r")
case object X extends Rating("x")
