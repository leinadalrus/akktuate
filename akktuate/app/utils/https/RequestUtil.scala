package pty.nfvd.app.utils.https

import com.typesafe.config.Config
import akka.util.Timeout

trait RequestTimeout {
	import scala.concurrent.duration._
	def requestTimeout(conf: Config): Timeout = {
		val time = conf.getString("scala.com.example.request-timeout")
		val dura = Duration(time)
		FiniteDuration(dura.length, dura.unit)
	}
}