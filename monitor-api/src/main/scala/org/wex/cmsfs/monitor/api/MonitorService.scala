package org.wex.cmsfs.monitor.api

import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait MonitorService extends Service {

  def createMonitor: ServiceCall[Monitor, Monitor]

  def monitorCollectEvents(): Topic[MonitorEvent]

  override final def descriptor = {
    import Service._
    named("monitor").withCalls(
      pathCall("/api/monitor", createMonitor _)
    ).withTopics(
      topic("monitor-collect", monitorCollectEvents)
    )
  }
}