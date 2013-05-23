package com.jmparsons.plugin

import sbt._
import sbt.Keys._
import play.Project._

object LesscPlugin extends Plugin {

  val lesscEntryPoints = SettingKey[PathFinder]("play-lessc-entry-points")
  val lesscOptions = SettingKey[Seq[String]]("play-lessc-options")
  val lesscVerbose = SettingKey[Boolean]("play-lessc-verbose")
  val LesscWatcher = play.Project.AssetsCompiler("lessc",
    (_ ** "*.less"),
    lesscEntryPoints in Compile,
    { (name, min) => name.replace(".less", if (min) ".min.css" else ".css") },
    { (file, options) => LesscCompiler.compile(file, options) },
    lesscOptions in Compile
  )

  override lazy val settings = Seq(
    lesscEntryPoints <<= (sourceDirectory in Compile).apply(base => ((base / "assets" ** "*.less") --- base / "assets" ** "_*")),
    lesscOptions := Seq.empty[String],
    lesscVerbose := false,
    resourceGenerators in Compile <+= LesscWatcher
  )

}