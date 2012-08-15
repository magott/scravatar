package scravatar

import java.security.MessageDigest

object Md5 {

  def hash(s: String) :String= {
    val md5 = MessageDigest.getInstance("MD5").digest(s.getBytes)
    asString(md5)
  }

  val hexDigits = "0123456789abcdef".toCharArray

  @Override def asString(bytes:Array[Byte]) = {
    val sb:StringBuilder = new StringBuilder(2 * bytes.length)
    bytes.foldLeft(""){case (agg, b) => agg + hexDigits((b >> 4) & 0xf) + hexDigits(b & 0xf)}
  }

}
