package pty.nfvd.app.models.actors.clients.clusters

import akka.actor._
import akka.cluster._
import akka.cluster.typed._
import akka.cluster.ClusterEvent._

object DockerClusterListener {
	def props = Props[DockerClusterListener]

	case class PgpdGet(str: String)
}

class DockerClusterListener extends Actor {
	import DockerClusterListener._

	def apply(): Behavior[ClusterEvent.ClusterDomainEvent] =
		Behaviors.setup { ctx =>
			Cluster(ctx.system).subscriptions ! Subscribe(ctx.self, classOf[ClusterEvent.ClusterDomainEvent])

			Behaviors.receiveMessagePartial {
				case PgpdGet(str: String) =>
					sender() ! "/GET/" + str
			}
		}
}