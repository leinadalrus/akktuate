package pty.nfvd.app.models.actors.clients.clusters

import akka.actor._
import akka.cluster._
import akka.cluster.typed._
import akka.cluster.ClusterEvent._

import com.nfvd.app.utils.api.RestfulApiRegistry
import com.nfvd.app.utils.api.RestfulHttpsPgpdEvent._

object ClusterListener {
	def props = Props[ClusterListener]

	case class PgpdGet(str: String)
}

@Singleton
class ClusterListener @Inject() extends Actor with ActorLogging {
	sealed trait Reference
	case class AcknowledgeActorReferee(reply: ActorRef[Reference])

	import ClusterListener._

	def apply(): Behavior[ClusterEvent.ClusterDomainEvent] =
		Behaviors.setup { ctx =>
			Cluster(ctx.system).subscriptions ! Subscribe(ctx.self, classOf[ClusterEvent.ClusterDomainEvent])

			Behaviors.receiveMessagePartial {
				case PgpdGet(str: String) =>
					sender() ! "/GET/" + str
			}
		} // This is also the accept(str: String) function, for consumer patterns.
	// ehhh ... we'll make an accept() function just in case.
	def accept = {
		case MemberUp(member) => log.info(s"$member UP.")
		case MemberExited(member) => log.info(s"$member EXITED.")
		case MemberRemoved(member, previousState) => 
			if (previousState == MemberStatus.Exiting) {
				log.info(s"$member exited and REMOVED.")
			} else
				log.info(s"$member downed yet was REMOVED afterwards.")

		case UnreachableMember(member) => log.info(s"$m UNREACHABLE")
		case reachableMember(member) => log.info(s"$m REACHABLE")
		case s: CurrentClusterState => log.info(s"Cluster state: $s")
	}

	def andThen(): Behavior[AcknowledgeActorReferee] = {
		RestfulApiRegistry()
	}

	override def postStop(): Unit = {
		Cluster(context.system).unsubscribe(self)
		super.postStop()
	}
}