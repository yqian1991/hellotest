import sbtrelease.{ReleaseStateTransformations, ReleaseStep}
import sbtrelease.ReleasePlugin._
import sbt._
import sbt.Keys._

object MyBuild extends Build {
  lazy val HelloTest = Project(
    id = "HelloTest",
    base = file("."),

    //makeDeploymentSettings(Universal, packageBin in Universal, "zip"),
    settings = Defaults.defaultSettings ++ releaseSettings ++ Seq(/* custom settings here */
      organization := "yu",
      scalaVersion := "2.11.6",
      libraryDependencies ++= List(
        mongoDb,
        log
      ),
      publishTo :=  {
        if (isSnapshot.value)
          Some(Resolver.file("file",  new File(".cache/repos/snapshots" )) )
        else
          Some(Resolver.file("file",  new File(".cache/repos/release" )) )
      },
      // Setup the packager
      //java_application,
      publishMavenStyle := false,
      // Enable JAR export for staging
      exportJars := true,
      crossScalaVersions := Seq("2.10.3"),
      ReleaseKeys.releaseProcess := Seq[ReleaseStep](
        ReleaseStateTransformations.checkSnapshotDependencies,
        ReleaseStateTransformations.inquireVersions,
        ReleaseStateTransformations.runTest,
        ReleaseStateTransformations.setReleaseVersion,
        ReleaseStateTransformations.commitReleaseVersion,
        ReleaseStateTransformations.tagRelease,
        ReleaseStateTransformations.publishArtifacts, // disable publishArtifacts until we have a nexus/artifactory
        ReleaseStateTransformations.setNextVersion,
        ReleaseStateTransformations.commitNextVersion,
        ReleaseStateTransformations.pushChanges
      )
    )
  )
  val mongoDb = "org.mongodb" %% "casbah"% "2.7.3"
  val log = "ch.qos.logback" % "logback-classic" % "1.1.2"

}
