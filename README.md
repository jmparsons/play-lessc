# play-lessc
This [sbt][sbt] plugin for [Play 2.1.x][play] provides the ability to use lessc command line tools to compile less through node instead of the default Rhino.

# Prerequisites
This plugin requires lessc in this case installed globally through node.js and npm.

    npm install -g lessc

# Installation
Add the resolver and sbt plugin to your `project/pugins.sbt` file:

    resolvers += "JMParsons Releases" at "http://jmparsons.github.io/releases/"

    addSbtPlugin("com.jmparsons" % "play-lessc" % "0.0.2")

# Usage
Import the plugin file into your Build.scala to override settings:

    import com.jmparsons.plugin.LesscPlugin._

Set the default `lessEntryPoints` to `Nil` and put in your custom ones into lesscEntryPoints:

    def customLessEntryPoints(base: File): PathFinder = (
      (base / "app" / "assets" / "stylesheets" * "*.less")
    )

    lazy val main = play.Project(appName, appVersion, mainDeps).settings(
      lessEntryPoints := Nil,
      lesscEntryPoints <<= baseDirectory(customLessEntryPoints)
    )

Less options can be passed in using `lesscOptions`:

    lesscOptions in Compile := Seq("--no-color")

A special `--verbose` option causes the plugin to display each `lessc`
command line on the console. For instance:

    lesscOptions in Compile := Seq("--no-color", "--verbose")


## Credits
This plugin is based off of [play-stylus][play-stylus] and [play-sass][play-sass].

## License
MIT: <http://jmparsons.mit-license.org> - [@jmparsons](http://twitter.com/jmparsons)

[play-sass]: https://github.com/jlitola/play-sass
[play-stylus]: https://github.com/patiencelabs/play-stylus
[play]: http://www.playframework.org/
[sbt]: https://github.com/harrah/xsbt