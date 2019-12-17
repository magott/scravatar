package scravatar

import org.scalatest.FunSuite
import java.io.{FileOutputStream}

class ScravatarTest extends FunSuite {

  val email = "morten@andersen-gott.com"

  test("Simple Avatar url") {
    val gravatar = Gravatar(email).ssl(true).default(Monster)
    assert(gravatar.defaultImage.isDefined)
  }

  test("All props are combined") {
    val gravatar = Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl
    assert(gravatar.contains("=monster"))
  }

  test("Download") {
    val fos = new FileOutputStream("//tmp/pic.jpg")
    fos.write(Gravatar(email).downloadImage)
  }

  test("Fails if size > 2048") {
    intercept[IllegalArgumentException] {
      Gravatar(email).size(2049)
    }
  }

  test("Fails if size < 0 ") {
    intercept[IllegalArgumentException] {
      Gravatar(email).size(-1)
    }
  }

  test("E-mail is trimmed and lower cased ") {
    val target = Gravatar(email)
    val upper = Gravatar("MORTEN@ANDERSEN-GOTT.com")
    val space = Gravatar(" %s ".format(email))
    assert(target.emailHash == upper.emailHash)
    assert(target.emailHash == space.emailHash)
  }

  test("URL is encoded"){
    val target = Gravatar(email).default(DefaultImage("http://example.com/image.jpg"))
    assert (target.defaultImage.get.value == "http%3A%2F%2Fexample.com%2Fimage.jpg")
  }

  test("URL is validated") {
    intercept[IllegalArgumentException]{
      DefaultImage("http://example.com}")
    }
  }
}
