import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.2.1"
isSnapshot := true

Compile / javaSource := baseDirectory.value / "src"
javacOptions ++= Seq("-g", "-Xlint:deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii", "--release", "11")
netLogoVersion := "6.3.0"
netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"
