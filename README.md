# play-lessc
This [sbt][sbt] plugin for [Play 2.1.x][play] provides the ability to use lessc command line tools to compile less through node instead of the default Rhino.

# Prerequisites
This plugin requires lessc in this case installed globally through node.js and npm.

    npm install -g lessc

# Installation
Add the resolver and sbt plugin to your `project/pugins.sbt` file:

    resolvers += "JMParsons Releases" at "http://jmparsons.github.io/releases/"

    addSbtPlugin("com.jmparsons" % "play-lessc" % "0.0.5")

# Usage
Import the plugin file into your Build.scala to override settings:

    import com.jmparsons.plugin.LesscPlugin._

Set the default `lessEntryPoints` to `Nil` and put in your custom ones into lesscEntryPoints:

    def customLessEntryPoints(base: File): PathFinder = (
      (base / "app" / "assets" / "stylesheets" * "*.less")
    )

    lazy val main = play.Project(appName, appVersion, mainDeps).settings(lesscSettings: _*).settings(
      lessEntryPoints := Nil,
      lesscEntryPoints in Compile <<= baseDirectory(customLessEntryPoints)
    )

Less options can be passed in using `lesscOptions`:

    lesscOptions in Compile := Seq("--no-color")

The `--verbose` option outputs each `lessc` command into the console:

    lesscOptions in Compile := Seq("--no-color", "--verbose")


## Changelog

0.0.6 - July 11, 2013

- Made settings optional instead of an override. Requires importing into settings now.

0.0.5 - July 8, 2013

- Added compliling of dependencies after modifying an imported file.
- Non minified files will include line comments by default.

0.0.4 - July 2, 2013

- Added trim to output to remove the default less newline.

## Credits
This plugin is based off of [play-stylus][play-stylus] and [play-sass][play-sass].

## License
MIT: <http://jmparsons.mit-license.org> - [@jmparsons](http://twitter.com/jmparsons)

[play-sass]: https://github.com/jlitola/play-sass
[play-stylus]: https://github.com/patiencelabs/play-stylus
[play]: http://www.playframework.org/
[sbt]: https://github.com/harrah/xsbt