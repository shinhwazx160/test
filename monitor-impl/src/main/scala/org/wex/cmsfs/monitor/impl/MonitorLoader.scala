package org.wex.cmsfs.monitor.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.broker.kafka.LagomKafkaComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import play.api.libs.ws.ahc.AhcWSComponents
import com.softwaremill.macwire._
import org.wex.cmsfs.monitor.api.MonitorService
import play.api.Environment

import scala.concurrent.ExecutionContext

//class MonitorLoader extends LagomApplicationLoader {
//  override def load(context: LagomApplicationContext): LagomApplication =
//    new MonitorApplication(context) {
//      override def serviceLocator: ServiceLocator = NoServiceLocator
//    }
//
//  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
//    new MonitorApplication(context) with LagomDevModeComponents
//}
//
//abstract class MonitorApplication(context: LagomApplicationContext) extends LagomApplication(context)
//  with CassandraPersistenceComponents
//  with AhcWSComponents
//  with LagomKafkaComponents {
//
//  override lazy val lagomServer = LagomServer.forServices(
//    bindService[MonitorService].to(wire[MonitorServiceImpl])
//  )
//
//  override lazy val jsonSerializerRegistry = MonitorSerializerRegistry
//
//  persistentEntityRegistry.register(wire[MonitorEntity])
//}

trait MonitorComponents extends LagomServerComponents
  with CassandraPersistenceComponents {

  implicit def executionContext: ExecutionContext

  def environment: Environment

  override lazy val lagomServer = LagomServer.forServices(
    bindService[MonitorService].to(wire[MonitorServiceImpl])
  )

  lazy val jsonSerializerRegistry = MonitorSerializerRegistry

  persistentEntityRegistry.register(wire[MonitorEntity])
}

abstract class MonitorApplication(context: LagomApplicationContext) extends LagomApplication(context)
  with MonitorComponents
  with AhcWSComponents
  with LagomKafkaComponents {
}

class MonitorApplicationLoader extends LagomApplicationLoader {
  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new MonitorApplication(context) with LagomDevModeComponents

  override def load(context: LagomApplicationContext): LagomApplication =
    new MonitorApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }
}