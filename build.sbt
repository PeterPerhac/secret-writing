lazy val root = (project in file(".")).settings(
  name := "SecretWriting",
  inThisBuild(List(organization := "uk.co.devproltd.experiments", scalaVersion := "2.12.8", version := "0.1.0-SNAPSHOT")),
  libraryDependencies += "com.monovore" %% "decline" % "0.5.0"
)
