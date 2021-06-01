package pty.nfvd.app.models.actors.clients.clusters

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.ClusterEvent
import akka.cluster.ClusterEvent._
import akka.cluster.typed.Cluster
import akka.cluster.typed.Subscribe

object ClusterCompose {
	def apply(): Behavior[ClusterEvent.ClusterDomainEvent] =
		Behaviors.setup { ctx =>
			ctx.log.debug("App now listening to: [Docker-compose] cluster ...")

			Cluster(ctx.system).subscriptions ! Subscribe(ctx.self, classOf[ClusterEvent.ClusterDomainEvent])
			// TODO: code ...
		}

	def accept(): Behavior[ClusterEvent.ClusterDomainEvent] =
		Behaviors.setup { ctx =>
			val cluster = akka.cluster.Cluster(ctx.system)
			val members = cluster.state.members.filer(_.status == MemberStatus.Up)

			cluster.join(cluster.selfAddress)

			cluster.registerOnMemberUp {
				cluster.subscribe(self, initialStateMode = InitialStateAsEvents, classOf[MemberEvent], classOf[UnreachableMember])
			}
		}

	def andThen() {
		clusterOfActorSystem.joinSeedNodes(clusterAddressList)
	}

	val actorSystem = ActorSystem()
	val clusterOfActorSystem = Cluster(actorSystem)
	val clusterAddressList: List[Address] = ???
}