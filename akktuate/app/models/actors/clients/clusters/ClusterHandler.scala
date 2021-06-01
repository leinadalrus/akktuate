package pty.nfvd.app.models.actors.clients.clusters

import akka.actor._
import play.api._
import play.api.mvc._
import javax.inject._
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import pty.nfvd.app.models.actors.clients.clusters.ClusterListener

object ClusterHandler {
 	case object GetConfig
}

@Override
class ClusterHandler @Inject() (clusterConfig: com.typesafe.config.Config) extends Actor with App with ClusterListener {
	import ClusterListenerHandler._

	val clusterConfig = ConfigFactory.load()
	val clusterName = clusterConfig.getString("clustering.cluster.name")
	val clusterOpts = clusterConfig.getOptional[String]("ClientClusterCompose.config").getOrElse(clusterConfig.getString("clustering.cluster.name"))
	
	def receive() = {
		case GetConfig =>
			sender() ! configOpts
	}

	ClusterListener() {
		// TODO(Daniel): code ...
	}

	def apply(strCtx: String): com.typesafe.config.Config = {
		clusterOpts = ConfigFactory.parseString(strCtx)
		// TODO(Daniel): get String Context and Configuration type thru each functor
		ClusterListener()
		ClusterListener.andThen()
	}

	ActorSystem(ClusterListener(), receive())
} // https://www.playframework.com/documentation/2.8.x/ScalaAkka#Integrating-with-Akka