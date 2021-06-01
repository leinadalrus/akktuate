@JSExport("AkktuateMain")
object AkktuateMain extends JSApp {
	@JSExport
	def main(): Unit = {
		GlobalStyles.addToDocument()

		val router = Router(BaseUrl.until_#, routerConfig)
		React.render(router(), dom.document.getElementById("index"))
	}
}