package controllers

import java.io.{ByteArrayInputStream, FileOutputStream, InputStream}
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.cert.X509Certificate
import java.security.spec.RSAPublicKeySpec
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
    val googlepk = "-----BEGIN CERTIFICATE-----\nMIIDJjCCAg6gAwIBAgIIYr5+DHiTpxowDQYJKoZIhvcNAQEFBQAwNjE0MDIGA1UE\nAxMrZmVkZXJhdGVkLXNpZ25vbi5zeXN0ZW0uZ3NlcnZpY2VhY2NvdW50LmNvbTAe\nFw0xODA5MzAxNDQ5MTFaFw0xODEwMTcwMzA0MTFaMDYxNDAyBgNVBAMTK2ZlZGVy\nYXRlZC1zaWdub24uc3lzdGVtLmdzZXJ2aWNlYWNjb3VudC5jb20wggEiMA0GCSqG\nSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCy+FjAr6hTV2HAoOlFmalqLiwNa1pE5IRt\nZ3IowdOxz2deioINbOJfgOYKdpHy0wS5/ZkCY9W6RzDc11ZEdW38raU9Ww/BxlS2\nca/ViQOl3AeP36FuX9qjVJdpF+UNlGEHaif1VQxEKTLgzl90d0hCEVxwnnLPyIeW\ndD5HmEcp7wFZPqnpNeVEAV0VRpdHmdm9C9dme+N0oC+/IJp458zGHlMVW4Sqh9MV\nVCMqstPCpJIRCm/XWWuRHPDVjTHk2tFEJiM7bLBCk8HD91DGd+cDltAEhdHeYG2J\nzbXxZd/JZZj3MmqpvOVAAkf6c+eKZ7eYUVfcd8yDBPJ0xEY0CWiJAgMBAAGjODA2\nMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMBYGA1UdJQEB/wQMMAoGCCsG\nAQUFBwMCMA0GCSqGSIb3DQEBBQUAA4IBAQAeWHL6Ck5FST8yHVWFw9lEpsUTl4n5\nDtH66dA38bnFsmEZwhTVDJWvbWfsFk20H5jYMeeLUGvmOb5YlLVoANDrA1AKk/DW\nQRTt6fczvTyV0o12OXxfzQlku5g643OLC4LF8i0yr3rdIxazuAz4KXI1Iubj/scc\nDAPmZg6gaWmccqGSRI6fSfOBYyvVRe1ahtYROA697T0byE9y5AkzZt3vpizy32RM\nbiwgozVPrFQRE25tc3PuihC7pIP87gdeNuegkxpwP/8c4vF2LPZgvZqxlFLmBpY3\nHMQr1CqXlPMB82y27cw9EDpRJAsudWFNbk3X2QwnM0cSKOyH0sP5L+OR\n-----END CERTIFICATE-----\n"
    val googlepk2 = "MIIDJjCCAg6gAwIBAgIIYr5+DHiTpxowDQYJKoZIhvcNAQEFBQAwNjE0MDIGA1UEAxMrZmVkZXJhdGVkLXNpZ25vbi5zeXN0ZW0uZ3NlcnZpY2VhY2NvdW50LmNvbTAeFw0xODA5MzAxNDQ5MTFaFw0xODEwMTcwMzA0MTFaMDYxNDAyBgNVBAMTK2ZlZGVyYXRlZC1zaWdub24uc3lzdGVtLmdzZXJ2aWNlYWNjb3VudC5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCy+FjAr6hTV2HAoOlFmalqLiwNa1pE5IRtZ3IowdOxz2deioINbOJfgOYKdpHy0wS5/ZkCY9W6RzDc11ZEdW38raU9Ww/BxlS2ca/ViQOl3AeP36FuX9qjVJdpF+UNlGEHaif1VQxEKTLgzl90d0hCEVxwnnLPyIeWdD5HmEcp7wFZPqnpNeVEAV0VRpdHmdm9C9dme+N0oC+/IJp458zGHlMVW4Sqh9MVVCMqstPCpJIRCm/XWWuRHPDVjTHk2tFEJiM7bLBCk8HD91DGd+cDltAEhdHeYG2JzbXxZd/JZZj3MmqpvOVAAkf6c+eKZ7eYUVfcd8yDBPJ0xEY0CWiJAgMBAAGjODA2MAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMCMA0GCSqGSIb3DQEBBQUAA4IBAQAeWHL6Ck5FST8yHVWFw9lEpsUTl4n5DtH66dA38bnFsmEZwhTVDJWvbWfsFk20H5jYMeeLUGvmOb5YlLVoANDrA1AKk/DWQRTt6fczvTyV0o12OXxfzQlku5g643OLC4LF8i0yr3rdIxazuAz4KXI1Iubj/sccDAPmZg6gaWmccqGSRI6fSfOBYyvVRe1ahtYROA697T0byE9y5AkzZt3vpizy32RMbiwgozVPrFQRE25tc3PuihC7pIP87gdeNuegkxpwP/8c4vF2LPZgvZqxlFLmBpY3HMQr1CqXlPMB82y27cw9EDpRJAsudWFNbk3X2QwnM0cSKOyH0sP5L+OR"
    val googlepk3 = "\nMIIDJjCCAg6gAwIBAgIIYr5+DHiTpxowDQYJKoZIhvcNAQEFBQAwNjE0MDIGA1UE\nAxMrZmVkZXJhdGVkLXNpZ25vbi5zeXN0ZW0uZ3NlcnZpY2VhY2NvdW50LmNvbTAe\nFw0xODA5MzAxNDQ5MTFaFw0xODEwMTcwMzA0MTFaMDYxNDAyBgNVBAMTK2ZlZGVy\nYXRlZC1zaWdub24uc3lzdGVtLmdzZXJ2aWNlYWNjb3VudC5jb20wggEiMA0GCSqG\nSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCy+FjAr6hTV2HAoOlFmalqLiwNa1pE5IRt\nZ3IowdOxz2deioINbOJfgOYKdpHy0wS5/ZkCY9W6RzDc11ZEdW38raU9Ww/BxlS2\nca/ViQOl3AeP36FuX9qjVJdpF+UNlGEHaif1VQxEKTLgzl90d0hCEVxwnnLPyIeW\ndD5HmEcp7wFZPqnpNeVEAV0VRpdHmdm9C9dme+N0oC+/IJp458zGHlMVW4Sqh9MV\nVCMqstPCpJIRCm/XWWuRHPDVjTHk2tFEJiM7bLBCk8HD91DGd+cDltAEhdHeYG2J\nzbXxZd/JZZj3MmqpvOVAAkf6c+eKZ7eYUVfcd8yDBPJ0xEY0CWiJAgMBAAGjODA2\nMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgeAMBYGA1UdJQEB/wQMMAoGCCsG\nAQUFBwMCMA0GCSqGSIb3DQEBBQUAA4IBAQAeWHL6Ck5FST8yHVWFw9lEpsUTl4n5\nDtH66dA38bnFsmEZwhTVDJWvbWfsFk20H5jYMeeLUGvmOb5YlLVoANDrA1AKk/DW\nQRTt6fczvTyV0o12OXxfzQlku5g643OLC4LF8i0yr3rdIxazuAz4KXI1Iubj/scc\nDAPmZg6gaWmccqGSRI6fSfOBYyvVRe1ahtYROA697T0byE9y5AkzZt3vpizy32RM\nbiwgozVPrFQRE25tc3PuihC7pIP87gdeNuegkxpwP/8c4vF2LPZgvZqxlFLmBpY3\nHMQr1CqXlPMB82y27cw9EDpRJAsudWFNbk3X2QwnM0cSKOyH0sP5L+OR"
    println("++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    println(googlepk)
    //    import java.util.Base64
    val modulus = "k7GJXpcx-RLFiPWHdME4iQaKFXTHzEwj-e3bu3f9MdQLYgOICF2CqzDDOdPqagd_yc83vP25FGBaWFxAfF9g5GK2YnrCWsQB42JS1giZRHUb-vwFF7ekaV5cJlfEpCZm1YIwryboEd1hkQFjnXFPSY3HeCIa8D21sO_U4xQKE77Upy8WZ5685Bw1Td-2XdE-4hfq5XlT6WEsN1MVjfBy_A97VbMX_0PhcMXAKXNJuWJENgRFPk3M0o-erUDgTS3QpA5zDgWpimQectExHP5aMKvQwI6uho-81-YyEtjbdhXoE1m0mUDpy6uiEzzfgS22tkaoCAke-0s8LxCrF6_WZQ"
    val exponent = "AQAB"
    val publicKey = new RSAPublicKeySpec(new BigInteger(1, modulus.getBytes("UTF-8"))
      , new BigInteger(1, exponent.getBytes("UTF-8")))

    import play.api.libs.concurrent.Execution.Implicits._
    //implicit val repositoryMetadataWrites = Json.writes[WSResponse]
    //implicit d : Writeable[WSResponse] = ???
    ws.url("https://www.googleapis.com/oauth2/v4/token").post(data)
      .map(x => {
        val idToken = (Json.parse(x.body) \ "id_token").as[String]
        val idTokenContents = idToken.split('.')
        val key = ""
        println(key)
        //val fo = new FileOutputStream("""/Users/sulabhkothari/googleoauth.pem""")
        //fo.write(key.getBytes("UTF-8"))
        //fo.close()
        import java.security.KeyFactory
        //        import java.security.PublicKey
        //        import java.security.spec.EncodedKeySpec
        import java.security.spec.X509EncodedKeySpec
        val keyFactory = KeyFactory.getInstance("RSA")
        import java.security.PublicKey
        import java.security.cert.CertificateFactory
        val f = CertificateFactory.getInstance("X.509")
        import java.io.FileInputStream
        val certificate = f.generateCertificate(
          //new FileInputStream("/Users/sulabhkothari/googleoauth2.pem"))
          new ByteArrayInputStream(googlepk.getBytes(StandardCharsets.UTF_8)))
          .asInstanceOf[X509Certificate]
        val pk = certificate.getPublicKey
        val publicKeyFromAlg = keyFactory.generatePublic(publicKey)
        import views.html.helper.input
        import javax.crypto.Cipher
        //        val cipher = Cipher.getInstance("RSA/None/PKCS1Padding")
        //        cipher.init(Cipher.ENCRYPT_MODE, publicKeyFromAlg)
        //        val cipherData =
        //          Base64.getEncoder.encode(cipher.doFinal((idTokenContents(0)+"."+idTokenContents(1)).getBytes("UTF-8")))
        //
        //        println("
        //
        //
        // ==========================================================")
        //
        //println(cipherData)
        //        println("==========================================================
        //
        //
        // ")
        println(pk)

        //val len = idToken.get.toString().length
        println(idToken)
        import pdi.jwt.{Jwt, JwtAlgorithm, JwtHeader, JwtClaim, JwtOptions}
        val payload = new String(BaseEncoding.base64().decode(idTokenContents(1)), "UTF-8")
        val header = new String(BaseEncoding.base64().decode(idTokenContents(0)), "UTF-8")
        println(header)

        println(Jwt.decode(idToken, modulus, Seq(JwtAlgorithm.RS256)))
        println(Jwt.isValid(idToken, pk))
        println(Jwt.decode(idToken, pk))


        //println(Jwt.validate(idToken, BaseEncoding.base64.encode("rrKtnRCj9v1taEfGdkhVb2g_"), Seq(JwtAlgorithm.RS256)))
        //val signature = new String(BaseEncoding.base64().decode(idTokenContents(2)),"UTF-8")
        //val decoded = Jwt.decode(idToken.get.toString().substring(1,len-1), "rrKtnRCj9v1taEfGdkhVb2g_", Seq(JwtAlgorithm.RS256))
        //Ok(Jwt.encode(header, payload, "rrKtnRCj9v1taEfGdkhVb2g", JwtAlgorithm.RS256))
        Ok(s"$payload,$header,${idTokenContents(2)}")
      })


    //Ok(postBody)
  }
}



