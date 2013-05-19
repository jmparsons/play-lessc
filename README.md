# Lessc Plugin
This [sbt][sbt] plugin for [Play 2.1.x][play] provides the ability to use lessc command line tools to compile less through node instead of the default Rhino.

# Prerequisites
This plugin requires lessc in this case installed globally through node.js and npm.

    npm install -g lessc

# Istallation
Add the resolver and sbt plugin to your `project/pugins.sbt` file:

    resolvers += "JMParsons Releases" at "http://jmparsons.github.io/releases/"

    addSbtPlugin("com.jmparsons" % "play-lessc" % "0.0.1")

# Usage
Import the plugin file into your Build.scala to override settings:

    import com.jmparsons.plugin.LesscPlugin._

Set the default lessEntryPoints to Nil and put in your custom ones into lesscEntryPoints:

    def customLessEntryPoints(base: File): PathFinder = (
      (base / "app" / "assets" / "stylesheets" * "*.less")
    )

    lazy val main = play.Project(appName, appVersion, mainDeps).settings(
      lessEntryPoints := Nil,
      lesscEntryPoints <<= baseDirectory(customLessEntryPoints)
    )

## License
MIT: <http://jmparsons.mit-license.org> - [@jmparsons](http://twitter.com/jmparsons)

[play]: http://www.playframework.org/
[sbt]: https://github.com/harrah/xsbt