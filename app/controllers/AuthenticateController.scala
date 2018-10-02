package controllers

import java.util.Base64

import com.google.common.io.BaseEncoding
import javax.inject.Inject
import play.api.http.Writeable
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.ws._

class AuthenticateController @Inject()(cc: ControllerComponents)(ws: WSClient) extends AbstractController(cc) {
  def index = Action.async { implicit request: Request[AnyContent] =>
    Ok(request.queryString.mkString("--------"))
    import play.api.libs.json._
    val data = Json.obj(
      "code" -> request.getQueryString("code").get,
      "client_id" -> "119377523796-7keba510jbjo1keqfq9u6dj2hb8p43l3.apps.googleusercontent.com",
      "client_secret" -> "rrKtnRCj9v1taEfGdkhVb2g_",
      "redirect_uri" -> "http://localhost:9000/authenticate",
      "grant_type" -> "authorization_code"
    )

    import play.api.libs.concurrent.Execution.Implicits._
    //implicit val repositoryMetadataWrites = Json.writes[WSResponse]
    //implicit d : Writeable[WSResponse] = ???
    ws.url("https://www.googleapis.com/oauth2/v4/token").post(data)
      .map(x => {
        val idToken = Json.parse(x.body) \ "id_token"
        val len = idToken.get.toString().length
        val idTokenContents = idToken.get.toString().substring(1, len - 1).split('.')

        import pdi.jwt.{Jwt, JwtAlgorithm, JwtHeader, JwtClaim, JwtOptions}
        val payload = new String(BaseEncoding.base64().decode(idTokenContents(1)), "UTF-8")
        val header = new String(BaseEncoding.base64().decode(idTokenContents(0)), "UTF-8")

        //val signature = new String(BaseEncoding.base64().decode(idTokenContents(2)),"UTF-8")
        //val decoded = Jwt.decode(idToken.get.toString().substring(1,len-1), "rrKtnRCj9v1taEfGdkhVb2g_", Seq(JwtAlgorithm.RS256))
        //Ok(Jwt.encode(header, payload, "rrKtnRCj9v1taEfGdkhVb2g", JwtAlgorithm.RS256))
        Ok(Jwt.encode("""{"typ":"JWT","alg":"HS256"}""", """{"user":1}""", "key", JwtAlgorithm.RS256))
      })


    //Ok(postBody)
  }
}
