package com.jmparsons.plugin

import sbt._
import sbt.Keys._
import play.Project._

object LesscPlugin extends Plugin {

  val lesscEntryPoints = SettingKey[PathFinder]("play-lessc-entry-points")
  val lesscOptions = SettingKey[Seq[String]]("play-lessc-options")
  val LesscWatcher = AssetsCompiler("lessc",
    (_ ** "*.less"),
    lesscEntryPoints in Compile,
    { (name, min) => name.replace(".less", if (min) ".min.css" else ".css") },
    { (file, options) => {
        val lessDirOption = options.filter{o => (o.startsWith("dir="))}
        val lessDir = if (!lessDirOption.isEmpty) lessDirOption.mkString("").stripPrefix("dir=").stripSuffix("/") + "/" else ""
        try {
          Seq(lessDir + "lessc", "-v").!!
          LesscCompiler.compile(file, lessDir + "lessc", options)
        } catch {
          case e @ (_ : java.lang.RuntimeException | _ : java.io.IOException) => {
            try {
              Seq(lessDir + "lessc.cmd", "-v").!!
              LesscCompiler.compile(file, lessDir + "lessc.cmd", options)
            } catch {
              case e @ (_ : java.lang.RuntimeException | _ : java.io.IOException) => play.core.less.LessCompiler.compile(file)
            }
          }
        }
      }
    },
    lesscOptions in Compile
  )

  lazy val lesscSettings = Seq(
    lesscEntryPoints <<= (sourceDirectory in Compile).apply(base => ((base / "assets" ** "*.less") --- base / "assets" ** "_*")),
    lesscOptions := Seq.empty[String],
    resourceGenerators in Compile <+= LesscWatcher
  )

}