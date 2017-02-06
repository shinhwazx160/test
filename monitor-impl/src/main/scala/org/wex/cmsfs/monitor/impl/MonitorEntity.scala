package org.wex.cmsfs.monitor.impl

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import org.wex.cmsfs.monitor.api.Monitor

class MonitorEntity extends PersistentEntity {

  override type Command = MonitorCommand
  override type Event = MonitorEvent
  override type State = Option[Monitor]

  override def initialState = None

  override def behavior: Behavior = {
    case None => notCreated
  }

  private val notCreated = {
    Actions().onCommand[CreateMonitor, Done] {
      case (CreateMonitor(monitor), ctx, _) =>
        ctx.thenPersist(MonitorCreated(monitor))(_ => ctx.reply(Done))
    }.onEvent {
      case (MonitorCreated(monitor), _) => Some(monitor)
    }
  }
}