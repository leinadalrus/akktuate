package com.nfvd.app.utils.api

case class CreateEvent(key: String, value: String)
case class GetEvent(key: String)

case class UpdateEvent(key: String, value: String)
case class PutEvent(key: String)

case class YieldEvent(key: String)
case class DestroyEvent(key: String)

case class PgpdEvent(key: String, value: String)
case class PgpdEventVector(events: Vector[PgpdEvent])

sealed trait PgpdEventResponse
case class PgpdEventLogged(event: PgpdEvent) extends PgpdEventResponse
case object PgpdEventExisting extends PgpdEventLogged
 
case object RestfulHttpsPgpdEvent

