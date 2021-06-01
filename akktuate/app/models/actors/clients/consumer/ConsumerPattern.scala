package pty.nfvd.app.models.actors.clients.clusters

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._

import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}

import pty.nfvd.app.utils.api.RestfulHttpsPgpdEvent

class ConsumerPattern {
	def accept(str: String) {
		case GetEvent =>
			import akka.pattern.ask
			import akka.pattern.pipe

			def getEvents = ctx.children.map {
				child => self.ask(GetEvent(child.path.name)).mapTo[Option[PgpdEvent]]
			}

			def convertIntoEvents(f: Future[Iterable[Option[PgpdEvent]]]) =
				f.map(_.flatten).map(l => Events(l.toVector))

			pipe(convertIntoEvents(Future.sequence(getEvents))) to sender()
	}

	def andThen() {
		import akka.pattern.ask
		import akka.pattern.pipe

		/*var filepath: String = "HTTP/V2.0/GET/events/"*/
		sealed trait Consumed
		sealed trait Okay
		
		pipe(convertIntoEvents(Future.sequence(getEvents))) { event =>
			onSuccess(getEvent(event)) {_.fold(complete(Consumed, event)) {x => complete(Okay, x)}}
		}
	}
}