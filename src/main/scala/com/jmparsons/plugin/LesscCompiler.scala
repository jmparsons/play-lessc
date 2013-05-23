package com.jmparsons.plugin

import sbt.PlayExceptions.AssetCompilationException
import java.io.File
import scala.sys.process._

object LesscCompiler {

  def compile(lesscFile: File, opts: Seq[String]): (String, Option[String], Seq[File]) = {
    val options = opts.filter { _ != "rjs" }
    try {
      val cmd = (Seq("lessc") ++ options ++ Seq(lesscFile)).mkString(" ")
      val cssOutput = captureOutput(cmd #< lesscFile)
      val compressedCssOutput = captureOutput((Seq("lessc -x") ++ options ++ Seq(lesscFile)).mkString(" ") #< lesscFile)
      (cssOutput, Some(compressedCssOutput), Seq(lesscFile))
    } catch {
      case e: LesscCompilationException => {
        throw AssetCompilationException(e.file.orElse(Some(lesscFile)), "Lessc compiler: " + e.message, Some(e.line), Some(e.column))
      }
    }
  }

  private def captureOutput(command: ProcessBuilder): String = {
    val err = new StringBuilder
    val out = new StringBuilder

    val capturer = ProcessLogger(
        (output: String) => out.append(output + "\n"),
        (error: String) => err.append(error + "\n"))

    val process = command.run(capturer)
    if (process.exitValue == 0)
      out.toString
    else
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