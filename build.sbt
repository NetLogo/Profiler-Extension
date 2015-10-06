enablePlugins(org.nlogo.build.NetLogoExtension)

name := "profiler"

netLogoClassManager := "org.nlogo.extensions.profiler.ProfilerExtension"

netLogoTarget :=
  org.nlogo.build.NetLogoExtension.directoryTarget(baseDirectory.value)

netLogoZipSources   := false

javaSource in Compile <<= baseDirectory(_ / "src")

javacOptions ++= Seq("-g", "-Xlint:deprecation", "-Xlint:all", "-Xlint:-serial", "-Xlint:-path",
  "-encoding", "us-ascii")

val netLogoJarURL =
  Option(System.getProperty("netlogo.jar.url")).getOrElse("http://ccl.northwestern.edu/netlogo/5.3.0/NetLogo.jar")

val netLogoJarOrDependency = {
  import java.io.File
  import java.net.URI
  if (netLogoJarURL.startsWith("file:"))
    Seq(unmanagedJars in Compile += new File(new URI(netLogoJarURL)))
  else
    Seq(libraryDependencies += "org.nlogo" % "NetLogo" % "5.3.0" from netLogoJarURL)
}

netLogoJarOrDependency
