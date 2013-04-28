name := "mcda-toolkit"

version := "1.0.0"

organization := "org.mcda"

scalaVersion := "2.10.0"

EclipseKeys.withSource := true

scalacOptions ++= Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  Seq(
    "ch.qos.logback"          %   "logback-classic" % "1.0.7",
    "org.scalaz"              %%  "scalaz-core"     % "7.0.0-M9",
    "org.specs2"              %%  "specs2"          % "1.13" % "test",
    "org.mockito"             %   "mockito-all"     % "1.9.5" % "test",
	"junit"                   %   "junit"           % "4.11" % "test"
  )
}