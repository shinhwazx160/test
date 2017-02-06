package org.wex.cmsfs.monitor.api

import java.util.UUID
import julienrf.json.derived
import play.api.libs.json._

sealed trait MonitorEvent {
  val id: UUID
}

case class MonitorCreated(id: UUID, monitorId: Int, connectorId: Int) extends MonitorEvent

object MonitorCreated {
  implicit val format: Format[MonitorCreated] = Json.format
}

//这个对象需要放在最后
object MonitorEvent {
  implicit val format: Format[MonitorEvent] =
    derived.flat.oformat((__ \ "type").format[String])
}