import sbtrelease.ReleasePlugin._
import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin._

object MyBuild extends Build {
  lazy val HelloTest = Project(
    id = "HelloTest",
    base = file("."),
    settings = Defaults.defaultSettings ++ releaseSettings ++ Seq(/* custom settings here */
      organization := "qianyu668899",
      version := "0.1.0",
      scalaVersion := "2.11.6",
      libraryDependencies ++= List(
        mongoDb,
        log
      )
      //publishTo := Some("My Github repos" at "https://github.com/qianyu668899/hellotest.git")
    )
  )

  val mongoDb = "org.mongodb" %% "casbah"% "2.7.3"
  val log = "ch.qos.logback" % "logback-classic" % "1.1.2"

}