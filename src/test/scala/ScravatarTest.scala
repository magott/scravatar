package no.magott.scravatar
import org.scalatest.FunSuite

class ScravatarTest extends FunSuite{

  val email = "hilde.andersengott@gmail.com"

  test("avatarUrl"){
    val gravatar = Gravatar(email).ssl(true).default(Monster)
    assert(gravatar.defaultImage.isDefined)
  }

  test("All props are combined"){
    Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl
  }
}
