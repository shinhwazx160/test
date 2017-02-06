package org.wex.cmsfs.monitor.api

import java.util.UUID
import com.lightbend.lagom.scaladsl.api.deser.PathParamSerializer
import play.api.libs.json.{Format, Json}
import JsonFormats._

case class Monitor(id: Option[UUID],
                   cron: String,
                   mode: String,
                   monitorId: Int,
                   connectorId: Int,
                   status: MonitorStatus.Status) {
  def convertStatus(newStatus: MonitorStatus.Status): Monitor = {
    copy(status = newStatus)
  }
}

object Monitor {
  implicit val format: Format[Monitor] = Json.format
}

object MonitorStatus extends Enumeration {
  val Created, Collecting, Completed, Cancelled = Value
  type Status = Value

  implicit val format: Format[Value] = enumFormat(this)
  implicit val pathParamSerializer: PathParamSerializer[Status] =
    PathParamSerializer.required("MonitorStatus")(withName)(_.toString)
}