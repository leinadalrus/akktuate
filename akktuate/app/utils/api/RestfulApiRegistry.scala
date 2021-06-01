package pty.nfvd.app.utils.api

import pty.nfvd.app.utils.api.RestfulJsonInfixOperation

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.collection.immutable
import java.io.Closeable

case class CreateRestful(key: String, value: String)
case class ReadRestful(key: String)
case class UpdateRestful(key: String)
case class DeleteRestful(key: String)

class RestfulApiRegistry {
	sealed trait Reference
	case class AcknowledgeActorReferee(reply: ActorRef[Reference])
	case class Referee(key: String, value: String)

	def close [K, V <: Closeable] (k: V => K) (v: V): K  = 
		try k(v)
		finally v.close // "How to write a class destructor in Scala?". Available at: https://stackoverflow.com/a/13162624
	

	def register(keys: Set[Referee]): Behavior[Reference] = 
		Behaviors.receiveMessage {
			case CreateRestful(key) =>
				register(key +> keys)
				
			case ReadRestful(key) =>
				Behaviors.same

			case UpdateRestful(key) =>
				Behaviors.same

			case DeleteRestful(key) =>
				close
		}

	def apply(): Behavior[AcknowledgeActorReferee] = register(key: Set[Referee]) { (ctx, msg) =>
		msg.replyTo ! Referee(msg.key, ctx.self)
		Behaviors.same
	}
}

