organization in ThisBuild := "org.wex"
version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"

val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
val cassandarCliect = "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0"

lazy val root = (project in file("."))
  .settings(name := "cmsfs")
  .aggregate(monitorApi, monitorImpl)

lazy val monitorApi = (project in file("monitor-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      playJsonDerivedCodecs
    )
  )

lazy val monitorImpl = (project in file("monitor-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      lagomScaladslKafkaBroker,
      cassandarCliect,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(monitorApi)


lagomCassandraCleanOnStart in ThisBuild := false

lagomKafkaEnabled in ThisBuild := false
lagomKafkaAddress in ThisBuild := "10.65.103.58:9092"