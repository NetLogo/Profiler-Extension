import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.3.1"
isSnapshot := true

scalaVersion := "3.7.0"

Compile / javaSource := baseDirectory.value / "src"
javacOptions ++= Seq("-g", "-encoding", "us-ascii", "--release", "17")
netLogoVersion      := "7.0.0-beta2-7e8f7a4"
netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"
