import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.2.1"
isSnapshot := true

Compile / javaSource := baseDirectory.value / "src"
javacOptions ++= Seq("-g", "-encoding", "us-ascii", "--release", "11")
netLogoVersion := "7.0.0-beta1"
netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"
