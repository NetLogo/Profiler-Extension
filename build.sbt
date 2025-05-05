import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.2.1"
isSnapshot := true

Compile / javaSource := baseDirectory.value / "src"
javacOptions ++= Seq("-g", "-Xlint:deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii", "--release", "11")
netLogoVersion := "7.0.0-internal1-df97144"
netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"
