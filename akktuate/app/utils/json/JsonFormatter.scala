package pty.nfvd.app.utils.json

import pty.nfvd.app.utils.data.RestfulApiRegistry
import spray.json.DefaultJsonProtocol

object JsonFormatter {
	import DefaultJsonProtocol._

	implicit val performAction = jsonFormat1(ActionPerformed)
}