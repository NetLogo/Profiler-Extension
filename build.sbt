import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.3.2"
isSnapshot := true

scalaVersion := "3.7.0"
Compile / scalaSource := baseDirectory.value / "src" / "scala"
scalacOptions ++= Seq("-deprecation", "-unchecked", "-Xfatal-warnings", "-encoding", "us-ascii", "-release", "21")

Compile / javaSource := baseDirectory.value / "src" / "java"
javacOptions ++= Seq("-g", "-encoding", "us-ascii", "--release", "21")

netLogoVersion      := "7.0.0-beta2-7e8f7a4"
netLogoClassManager := "ProfilerExtension"
