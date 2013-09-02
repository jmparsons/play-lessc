package com.jmparsons.plugin

import sbt.PlayExceptions.AssetCompilationException
import sbt.SettingKey
import java.io.File
import scala.sys.process._

object LesscCompiler {

  def compile(lesscFile: File, lessCmd: String, opts: Seq[String]): (String, Option[String], Seq[File]) = {
    val verbose = opts.contains("--verbose")
    val options = opts.filter{ o => (o != "rjs") && (o != "--verbose") && (!o.startsWith("dir=")) }.distinct
    try {
      if (verbose) println("+ " + (Seq(lessCmd) ++ options ++ Seq(lesscFile)).mkString(" "))
      val noMinOptions = options.filter{ o => (o != "-x") && (o != "--compress") && (o != "--yui-compress")}
      val (cssOutput, dependencies) = captureOutput((Seq(lessCmd, "-line-numbers=comments") ++ noMinOptions ++ Seq(lesscFile)).mkString(" "))
      val (compressedCssOutput, ignored) = captureOutput((Seq(lessCmd, "-x") ++ options ++ Seq(lesscFile)).mkString(" "))
      (cssOutput, Some(compressedCssOutput), dependencies.map{ new File(_) })
    } catch {
      case e: LesscCompilationException => {
        throw AssetCompilationException(e.file.orElse(Some(lesscFile)), "Lessc compiler: " + e.message, Some(e.line), Some(e.column))
      }
    }
  }

  private val DependencyLine = """^[\s]*?/\* line \d+, (.*) \*/$""".r

  private def captureOutput(command: ProcessBuilder): (String, Seq[String]) = {
    val err = new StringBuilder
    val out = new StringBuilder

    val capturer = ProcessLogger(
      (output: String) => out.append(output + "\n"),
      (error: String) => err.append(error + "\n"))

    val process = command.run(capturer)
    if (process.exitValue == 0) {
      val dependencies = out.lines.collect {
        case DependencyLine(f) => f
      }
      (out.mkString.trim, dependencies.toList.distinct)
    } else
      throw new LesscCompilationException(err.toString)
  }

  private val LocationLine = """\s*on line (\d+) of (.*)""".r

  private class LesscCompilationException(stderr: String) extends RuntimeException {

    val (file: Option[File], line: Int, column: Int, message: String) = parseError(stderr)

    private def parseError(error: String): (Option[File], Int, Int, String) = {
      var line = 0
      var seen = 0
      var column = 0
      var file : Option[File] = None
      var message = "Unknown error, try running lessc directly"
      for (errline: String <- augmentString(error).lines) {
        errline match {
          case LocationLine(l, f) => { line = l.toInt; file = Some(new File(f)); }
          case other if (seen == 0) => { message = other; seen += 1 }
          case other =>
        }
      }
      (file, line, column, message)
    }
  }

}