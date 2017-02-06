package org.wex.cmsfs.monitor.impl

import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import org.wex.cmsfs.monitor.api.Monitor

import scala.collection.immutable.Seq

object MonitorSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = List(
    //state
    JsonSerializer[Monitor],

    //commands and replies
    JsonSerializer[CreateMonitor],

    //events
    JsonSerializer[MonitorCreated]
  )

}
