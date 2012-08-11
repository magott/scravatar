package no.magott.scravatar
import org.scalatest.FunSuite

class ScravatarTest extends FunSuite{

  val email = "code@andersen-gott.com"

  test("Simple Avatar url"){
    val gravatar = Gravatar(email).ssl(true).default(Monster)
    assert(gravatar.defaultImage.isDefined)
    gravatar.avatarUrl.foreach(print)
  }

  test("All props are combined"){
    Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl
  }
}
