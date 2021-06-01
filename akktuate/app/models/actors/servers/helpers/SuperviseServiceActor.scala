package pty.nfvd.app.models.actors.servers.helpers

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.typed.{Behavior, DispatcherSelector, Dispatchers, PostStop, Signal, SupervisorStrategy}
import akka.event.Logging
import akka.util.Timeout
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
/* TODO */
object  ServiceSupervisorActor {
  def apply(): Behavior[String] = Behaviors.setup(ctx => new SuperviseServiceActor(ctx))
}

class SuperviseServiceActor(ctx: ActorContext[String]) extends AbstractBehavior[String](ctx) {
  private val childActor = ctx.spawn(Behaviors.supervise(ChildrenSupervisedActor.apply(ctx)).onFailure(SupervisorStrategy.restart), name = "supervisor-child")

  override def onMessage(msg: String): Behavior[String] = msg match {
    case "childFailed" => childActor ! "fail"
      this
  }
}

case class ChildrenSupervisedActor(ctx: ActorContext[String]) extends AbstractBehavior[String](ctx) {
  override def onMessage(msg: String): Behavior[String] = msg match {
    case "fail" => throw new Exception("Failure") // Make this error as data
  }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    /* case PreStart => /* code ... */ this */
    case PostStop => /* code ... */ this
  }
}