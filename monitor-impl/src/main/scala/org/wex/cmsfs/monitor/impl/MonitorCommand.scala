package org.wex.cmsfs.monitor.impl

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import org.wex.cmsfs.monitor.api.Monitor
import play.api.libs.json.{Format, Json}

sealed trait MonitorCommand

case class CreateMonitor(monitor: Monitor) extends MonitorCommand with ReplyType[Done]

object CreateMonitor {
  implicit val format: Format[CreateMonitor] = Json.format
}