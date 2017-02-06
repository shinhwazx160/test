package org.wex.cmsfs.monitor.impl

import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, ReadSideProcessor}
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}

import scala.concurrent.ExecutionContext

/**
  * Created by zhangxu on 2017/2/6.
  */
class MonitorEventProcessor (session: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext)
  extends ReadSideProcessor[MonitorEvent] {
  override def buildHandler(): ReadSideHandler[MonitorEvent] = ???

  override def aggregateTags: Set[AggregateEventTag[MonitorEvent]] = MonitorEvent.Tag.allTags
}
