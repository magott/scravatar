package scravatar
import org.scalatest.FunSuite
import java.io.{FileOutputStream}

class ScravatarTest extends FunSuite{

  val email = "morten@andersen-gott.com"

  test("Simple Avatar url"){
    val gravatar = Gravatar(email).ssl(true).default(Monster)
    assert(gravatar.defaultImage.isDefined)
  }

  test("All props are combined"){
    val gravatar = Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl
    assert(gravatar.contains("=monster"))
  }

  test("Download"){
    val fos = new FileOutputStream("//tmp/pic.jpg")
    fos.write(Gravatar(email).downloadImage)
  }

  test("Fails if size > 2048"){
    val exp = intercept[IllegalArgumentException] {
      Gravatar(email).size(2049)
    }
  }
    test("Fails if size < 0 "){
    val exp = intercept[IllegalArgumentException] {
      Gravatar(email).size(-1)
    }

  }

}
