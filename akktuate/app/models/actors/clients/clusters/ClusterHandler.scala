package pty.nfvd.app.models.actors.clients.clusters

import akka.actor._
import play.api._
import play.api.mvc._
import javax.inject._
import com.typesafe.config.ConfigFactory

object ClusterListenerHandler {
 	case object GetConfig
}

class ClusterListenerHandler @Inject() (clusterConfig: Configuration) extends Actor with App {
	import ClusterListenerHandler._

	val clusterConfig = ConfigFactory.load()
	val clusterName = clusterConfig.getString("clustering.cluster.name")
	val clusterOpts = clusterConfig.getOptional[String]("ClientClusterCompose.config").getOrElse(clusterConfig.getString("clustering.cluster.name"))
	
	def receive() = {
		case GetConfig =>
			sender() ! configOpts
	}

	ActorSystem(DockerClusterListener(), receive())
} // https://www.playframework.com/documentation/2.8.x/ScalaAkka#Integrating-with-Akka