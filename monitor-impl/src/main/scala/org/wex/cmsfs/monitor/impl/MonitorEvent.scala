package org.wex.cmsfs.monitor.impl

import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag}
import org.wex.cmsfs.monitor.api.Monitor
import play.api.libs.json.{Format, Json}

sealed trait MonitorEvent extends AggregateEvent[MonitorEvent] {
  override def aggregateTag = MonitorEvent.Tag
}

object MonitorEvent {
  val NumShards = 1
  val Tag = AggregateEventTag.sharded[MonitorEvent](NumShards)
}

case class MonitorCreated(monitor: Monitor) extends MonitorEvent

object MonitorCreated {
  implicit val format: Format[MonitorCreated] = Json.format
}