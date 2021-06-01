package pty.nfvd.app.models.actors.clients.helpers

import scala.collection.immutable.HashMap
import akka.actor.{Actor, ActorRef, ReceiveTimeout}
import pty.nfvd.app.sources.components.events.{EventMessage, ResultMessage}

class Lookup(uuid: Int) extends Actor {
  lazy val streamFifos = HashMap[String, Seq[String]]()

  def receive: Receive = {
    case EventMessage(uuid: Int, message: String) =>
      val curse = for {
        curse <- streamFifos.get("UUID").toList
      } yield curse
      ResultMessage(curse.take(uuid))
  }

  def gate(node: String*) {
    var x = HashMap[String, Seq[String]]()
    for (n <- node; (k, v) <- n.split()) {
      val l = n.getBytes(k) | Seq()
    }

    x
  }

  uuid match {
    case 0 => gate(uuid.toString()) // 0 = false
    case 1 => gate(uuid.toString()) // 1 = true
  }
}

class Finder(idx: Int) {
  def apply(idx: Int): (Int, Int) = {
    var iter = index(idx)
    var offs: Int = offset(idx)

    return (iter, offs)
  }

  def index(idx: Int) {
    return idx /= idx.byteValue()
  }

  def offset(idx: Int) {
    return idx %= idx.byteValue()
  }
}

class Reader(bArr: Array[Byte], idx: Int) {
	import pty.nfvd.app.models.actors.clients.helpers.Finder

	def apply(bArr: Array[Byte], idx: Int) {
		(i, b) => Finder(bArr, idx)
		return (bArr & (1 << b)) >> b
	}
}

class Writer(bArr: Array[Byte], idx: Int) {
	import pty.nfvd.app.models.actors.clients.helpers.Finder

	def apply(bArr: Array[Byte], idx: Int): Array[Byte] = {
		case Finder(i) => {
			var b = for {i <- bArr[i] | (1 << b)} yield b
			bArr = b
		}

		return bArr
	}
}