// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/sulabhkothari/IdeaProjects/oauth2/conf/routes
// @DATE:Sun Sep 30 23:31:46 IST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
