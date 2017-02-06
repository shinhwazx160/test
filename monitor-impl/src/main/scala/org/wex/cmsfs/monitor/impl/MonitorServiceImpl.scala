package org.wex.cmsfs.monitor.impl

import java.util.UUID

import akka.persistence.query.Offset
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import org.wex.cmsfs.monitor.api
import org.wex.cmsfs.monitor.api._

import scala.concurrent.{ExecutionContext, Future}

class MonitorServiceImpl(registry: PersistentEntityRegistry)(implicit ec: ExecutionContext) extends MonitorService {
  override def createMonitor: ServiceCall[Monitor, Monitor] = ServiceCall { m =>
    val id = UUIDs.timeBased()
    val monitor = Monitor(Some(id), m.cron, m.mode, m.monitorId, m.connectorId, MonitorStatus.Created)
    val command = CreateMonitor(monitor)
    entityRef(id).ask(command).map(_ => monitor)
  }

  override def monitorCollectEvents = TopicProducer.taggedStreamWithOffset(MonitorEvent.Tag.allTags.toList) { (tag, offset) =>
    println(11)
    registry.eventStream(tag, offset).mapAsync(1)(p => Future{
      println(offset);
      (api.MonitorCreated(UUIDs.random(), 1, 1), offset)
    })
  }


    private def convertEvent(eventStreamElement: EventStreamElement[MonitorEvent]): Future[(api.MonitorEvent, Offset)] = {
      eventStreamElement match {
        case EventStreamElement(_, MonitorCreated(m), offset) =>
          Future((api.MonitorCreated(m.id.get, m.monitorId, m.connectorId), offset))
      }
    }

  private def entityRef(id: UUID) = entityRefString(id.toString)

  private def entityRefString(id: String) = registry.refFor[MonitorEntity](id)
}
