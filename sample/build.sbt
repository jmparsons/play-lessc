name := "lessc-sample"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)

def customLessEntryPoints(base: File): PathFinder = (
  (base / "app" / "assets" / "stylesheets" * "*.less")
)

play.Project.playScalaSettings ++ lesscSettings

lessEntryPoints := Nil

lesscEntryPoints in Compile <<= baseDirectory(customLessEntryPoints)

lesscOptions in Compile := Seq("--yui-compress", "--verbose")