import akka.actor._
import javax.inject._
import play.api.Configuration

object ClusterListenerHandler {
 	case object GetConfig
}

class ClusterListenerHandler @Inject() (config: Configuration) extends Actor {
	import ClusterListenerHandler._

	val conf = config.getOptional[String]("pty.nfvd.app.config").getOrElse("none")

	def receive = {
		case GetConfig =>
			sender() ! conf
	}
}