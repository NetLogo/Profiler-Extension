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
  }

  override def unload(em: ExtensionManager): Unit = {
  }

  private object ProfilerStart extends Command {
    override def getSyntax = commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
    }
  }

  private object ProfilerStop extends Command {
    override def getSyntax = commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
    }
  }

  private object ProfilerReset extends Command {
    override def getSyntax = SyntaxJ.commandSyntax()
    override def perform(args: Array[Argument], context: Context): Unit = {
    }

  }

  private object ProfilerReport extends Reporter {
    override def getSyntax = reporterSyntax(ret = StringType)
    override def report(args: Array[Argument], context: Context) = {
      ""
    }
  }

  private object ProfilerData extends Reporter {
    override def getSyntax = reporterSyntax(ret = ListType)
    override def report(args: Array[Argument], context: Context) = {
      LogoList.Empty
    }
  }

  private object ProfilerProcedureCalls extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      Double.box(0)
    }
  }

  private object ProfilerProcedureExclusiveTime extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      Double.box(0)
    }
  }

  private object ProfilerProcedureInclusiveTime extends Reporter {
    override def getSyntax = reporterSyntax(right = List(StringType), ret = NumberType)
    override def report(args: Array[Argument], context: Context) = {
      Double.box(0)
    }
  }

}
