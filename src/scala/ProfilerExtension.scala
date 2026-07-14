import java.io.{ ByteArrayOutputStream, PrintStream }
import java.util.Arrays
import java.util.stream.Stream

import org.nlogo.api.{ Argument, Context, Command, DefaultClassManager, ExtensionException, ExtensionManager
                     , LogoException, PrimitiveManager, Reporter, Version }
import org.nlogo.core.{ LogoList, Syntax, SyntaxJ }
import Syntax.{ commandSyntax, ListType, NumberType, reporterSyntax, StringType }
import org.nlogo.nvm.{ ExtensionContext, Tracer, Workspace }
import org.nlogo.workspace.{ ExtensionManager => WSEM }

class ProfilerExtension extends DefaultClassManager {

  override def load(primManager: PrimitiveManager): Unit = {
    primManager.addPrimitive("start",          ProfilerStart)
    primManager.addPrimitive("stop",           ProfilerStop)
    primManager.addPrimitive("reset",          ProfilerReset)
    primManager.addPrimitive("report",         ProfilerReport)
    primManager.addPrimitive("data",           ProfilerData)
    primManager.addPrimitive("calls",          ProfilerProcedureCalls)
    primManager.addPrimitive("exclusive-time", ProfilerProcedureExclusiveTime)
    primManager.addPrimitive("inclusive-time", ProfilerProcedureInclusiveTime)
  }

  override def runOnce(em: ExtensionManager): Unit = {
    val tracer = new QuickTracer()
    em.asInstanceOf[WSEM].workspace.setProfilingTracer(tracer)
    // we disable it once it is installed, so that
    // we don't start collecting profiling data until
    // ProfilerStart is called.  -- CLB
    tracer.disable()
  }

  override def unload(em: ExtensionManager): Unit = {
    em.asInstanceOf[WSEM].workspace.setProfilingTracer(null)
  }

  private object ProfilerStart extends Command {
    override def getSyntax = commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
      if (!Version.useGenerator) {
        val msg = """The profiler extension requires the NetLogo bytecode generator, which is currently turned off.
                    |See the org.nlogo.noGenerator property for more info.""".stripMargin
        throw new ExtensionException(msg)
      }
      tracerOpt(context).foreach {
        _.enable()
      }
    }
  }

  private object ProfilerStop extends Command {
    override def getSyntax = commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
      tracerOpt(context).foreach {
        _.disable()
      }
    }
  }

  private object ProfilerReset extends Command {
    override def getSyntax = SyntaxJ.commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
      tracerOpt(context).foreach {
        _.reset()
      }
    }

  }

  private object ProfilerReport extends Reporter {
    override def getSyntax = reporterSyntax(ret = StringType)
    override def report(args: Array[Argument], context: Context) = {
      tracerOpt(context).fold("") {
        tracer =>
          val outArray = new ByteArrayOutputStream()
          tracer.dump(new PrintStream(outArray))
          outArray.toString()
      }
    }
  }

  private object ProfilerData extends Reporter {
    override def getSyntax = reporterSyntax(ret = ListType)
    override def report(args: Array[Argument], context: Context) = {
      import scala.jdk.CollectionConverters.SetHasAsScala
      tracerOpt(context).fold(LogoList.Empty) {
        tracer =>
          val headers = LogoList("procedure", "calls", "inclusive_time", "exclusive_time")
          val rows    =
            tracer.procedureNames().asScala.toSeq.map {
              procedureName =>
                LogoList( procedureName
                        , Double.box(tracer.calls(procedureName).toDouble)
                        , Double.box(tracer.inclusiveTime(procedureName) / 1e6)
                        , Double.box(tracer.exclusiveTime(procedureName) / 1e6)
                        )
            }
          LogoList((headers +: rows)*)
      }
      //final Workspace workspace = ((ExtensionContext) context).workspace()
      //final QuickTracer tracer = ((QuickTracer) workspace.profilingTracer())
    }
  }

  private object ProfilerProcedureCalls extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      val name = args(0).getString.toUpperCase
      tracerOpt(context).fold(Double.box(0)) {
        _.calls(name).toDouble
      }
    }
  }

  private object ProfilerProcedureExclusiveTime extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      val name = args(0).getString.toUpperCase
      tracerOpt(context).fold(Double.box(0)) {
        _.exclusiveTime(name) / 1e6
      }
    }
  }

  private object ProfilerProcedureInclusiveTime extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      val name = args(0).getString.toUpperCase
      tracerOpt(context).fold(Double.box(0)) {
        _.inclusiveTime(name) / 1e6
      }
    }
  }

  private def tracerOpt(context: Context): Option[Tracer] =
    Option(context.asInstanceOf[ExtensionContext].workspace.profilingTracer)

}
