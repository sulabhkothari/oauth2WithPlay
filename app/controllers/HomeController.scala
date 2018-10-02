package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    //Ok("https://www.google.com")
    Results.Redirect("https://accounts.google.com/o/oauth2/v2/auth?" +
      "client_id=119377523796-7keba510jbjo1keqfq9u6dj2hb8p43l3.apps.googleusercontent.com" +
      "&response_type=code&scope=openid%20email" +
      "&redirect_uri=http://localhost:9000/authenticate" +
      "&state=security_token%3D138r5719ru3e1%26url%3Dhttps://oauth2-login-demo.example.com/myHome" +
      //"&login_hint=jsmith@example.com" +
      //"&openid.realm=example.com" +
      "&nonce=0394852-3190485-2490358")

    //java.security.MessageDigest.getInstance("RS256")
      //+ "&hd=example.com")

//    Results.Redirect(
//      "https://accounts.google.com/o/oauth2/auth" +
//        "?redirect_uri=http://localhost:9000/authenticate&response_type=code" +
//        "&client_id=119377523796-7keba510jbjo1keqfq9u6dj2hb8p43l3.apps.googleusercontent.com" +
//        "&scope=https://www.googleapis.com/auth/analytics.readonly+https://www.googleapis.com/auth/userinfo.email" +
//        "&approval_prompt=force&access_type=offline")
    //Ok(views.html.index())
  }
}
