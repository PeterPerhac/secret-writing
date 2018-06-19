lazy val root = (project in file(".")).settings(
  name := "SecretWriting",
  inThisBuild(List(organization := "uk.co.devproltd.experiments", scalaVersion := "2.12.5", version := "0.1.0-SNAPSHOT")),
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
)
