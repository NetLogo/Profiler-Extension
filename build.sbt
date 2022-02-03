import org.nlogo.build.{ NetLogoExtension, ExtensionDocumentationPlugin }

enablePlugins(NetLogoExtension, ExtensionDocumentationPlugin)

name := "profiler"
version := "1.2.1"
isSnapshot := true

javaSource in Compile := baseDirectory.value / "src"
javacOptions ++= Seq("-g", "-Xlint:deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii", "--release", "11")
netLogoVersion := "6.2.1"
netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"
