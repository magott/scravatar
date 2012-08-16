package scravatar
import java.net.{URLDecoder, URI, URLEncoder}
import scala.Option

/**
 *
 * Written by Hamnis, copied from https://github.com/javaBin/ems-redux
 */
private case class URIBuilder private(scheme: Option[String], host: Option[String], port: Option[Int], path: List[Segment], params: Map[String, List[String]], pathEndsWithSlash: Boolean = false) {
  def withScheme(scheme: String) = copy(scheme = Some(scheme))

  def withHost(host: String) = copy(host = Some(host))
  def withPort(port: Int) = copy(port = Some(port))

  def segments(segments: String*) = copy(path = path ::: segments.map(Segment(_)).toList)

  def path(path: String): URIBuilder = {
    val (segments, endsWithSlash) = URIBuilder.decodePath(path)
    copy(path = this.path ::: segments, pathEndsWithSlash = endsWithSlash)
  }

  def replacePath(path: String): URIBuilder = {
    val (segments, endsWithSlash) = URIBuilder.decodePath(path)
    copy(path = segments, pathEndsWithSlash = endsWithSlash)
  }

  def emptyPath() = copy(path = Nil)

  def emptyParams() = copy(params = Map.empty)

  def queryParam(name: String, value: String): URIBuilder = queryParam(name, Some(value))

  def queryParam(name: String, value: Option[String]): URIBuilder = {
    //TODO: Maybe the None case should remove all values?
    val values = params.get(name).getOrElse(Nil) ++ value.toList

    if (value.isEmpty) this else copy(params = params + (name -> values))
  }

  def replaceSegments(segments: Segment*) = copy(path = segments.toList)

  def replaceQueryParam(name: String, value:String) = copy(params = params + (name -> List(value)))

  def build() = {
    def mkParamString() = {
      params.map{case (k, v) => v.map(i => "%s=%s".format(k, i)).mkString("&")}.mkString("&")
    }

    val par = if (params.isEmpty) None else Some(mkParamString()).filterNot(_.isEmpty)
    new URI(
      scheme.getOrElse(null),
      null,
      host.getOrElse(null),
      port.getOrElse(-1),
      if (path.isEmpty) null else path.map(_.encoded).mkString("/", "/", if (pathEndsWithSlash) "/" else ""),
      par.getOrElse(null),
      null
    )
  }
  override def toString = build().toString
}

private object URIBuilder {
  val KeyValue = """(?i)(\w+)=(.*)?""".r
  def apply(uri: URI): URIBuilder = {
    val (path, endsWithSlash) = decodePath(uri.getPath)
    def buildMap: (String) => Map[String, scala.List[String]] = s => {
      val arr: Array[String] = s.split("&")
      arr.foldLeft(Map[String, List[String]]()) {
        case (m, part) => part match {
          case KeyValue(k, "") => m + (k -> m.get(k).getOrElse(Nil))
          case KeyValue(k, v) => m + (k -> (m.get(k).getOrElse(Nil) ++ List(v)))
        }
      }
    }
    val params = Option(uri.getQuery).map(buildMap).getOrElse(Map[String, List[String]]())
    new URIBuilder(Option(uri.getScheme), Option(uri.getHost), Option(uri.getPort).filterNot(_ == -1), path, params, endsWithSlash)
  }

  def fromPath(path: String): URIBuilder = {
    empty.path(path)
  }

  private def decodePath(path: String): (List[Segment], Boolean) = {
    Option(path).filterNot(_.trim.isEmpty).map{ p =>
      if(p.startsWith("/")) p.substring(1) else p
    }.map(p => p.split("/").map(Segment.decoded(_)).toList -> p.endsWith("/")).getOrElse(Nil -> false)
  }

  def empty = new URIBuilder(None, None, None, Nil, Map())
}

case class Segment(seg: String) {
  def encoded = URLEncoder.encode(seg, "UTF-8")
}

object Segment {
  def decoded(seg: String) = Segment(URLDecoder.decode(seg, "UTF-8"))
}
