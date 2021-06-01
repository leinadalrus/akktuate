package pty.nfvd.app.utils.events

case class EventMessage(id: Int, message: String)
case class ResultMessage(curse: List[Seq[String]])

