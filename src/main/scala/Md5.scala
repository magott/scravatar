package scravatar

import java.security.MessageDigest

private object Md5 {

  def hash(s: String) :String= {
    val md5 = MessageDigest.getInstance("MD5").digest(s.getBytes)
    asString(md5)
  }

  val hexDigits = "0123456789abcdef".toCharArray

  def asString(bytes:Array[Byte]) = {
    bytes.foldLeft(""){case (agg, b) => agg + hexDigits((b >> 4) & 0xf) + hexDigits(b & 0xf)}
  }

}
