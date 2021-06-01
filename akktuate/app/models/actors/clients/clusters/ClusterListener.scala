package pty.nfvd.app.models.actors.clients.clusters

import akka.actor._
import akka.cluster._
import akka.cluster.typed._
import akka.cluster.ClusterEvent._

import com.nfvd.app.utils.api.RestfulApiRegistry
import com.nfvd.app.utils.api.RestfulHttpsPgpdEvent._

object DockerClusterListener {
	def props = Props[DockerClusterListener]

	case class PgpdGet(str: String)
}

@Singleton
class DockerClusterListener @Inject() extends Actor {
	sealed trait Reference
	case class AcknowledgeActorReferee(reply: ActorRef[Reference])

	import DockerClusterListener._

	def apply(): Behavior[ClusterEvent.ClusterDomainEvent] =
		Behaviors.setup { ctx =>
			Cluster(ctx.system).subscriptions ! Subscribe(ctx.self, classOf[ClusterEvent.ClusterDomainEvent])

			Behaviors.receiveMessagePartial {
				case PgpdGet(str: String) =>
					sender() ! "/GET/" + str
			}
		} // This is also the accept(str: String) function, for consumer patterns.

	def andThen(): Behavior[AcknowledgeActorReferee] = {
		RestfulApiRegistry()
	}
}