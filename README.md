# play-lessc
This [sbt][sbt] plugin for [Play][play] provides the ability to use command line tools to compile less through Node instead of Rhino.

# Prerequisites
This plugin requires lessc - [Less][less] command line program - which can be installed through Node.js and npm:

Globally:

    npm install -g less

Locally:

    npm install less

# Installation
Add the resolver and sbt plugin to your `project/plugins.sbt` file:

    resolvers += "JMParsons Releases" at "http://jmparsons.github.io/releases/"

    addSbtPlugin("com.jmparsons" % "play-lessc" % "0.1.1")

# Usage
Import the plugin file into your build file to override settings:

    import com.jmparsons.plugin.LesscPlugin._

Set the default `lessEntryPoints` to `Nil` and put in your custom ones into lesscEntryPoints:

    def customLessEntryPoints(base: File): PathFinder = (
      (base / "app" / "assets" / "stylesheets" * "*.less")
    )

build.sbt example:

    play.Project.playScalaSettings ++ lesscSettings

    lessEntryPoints := Nil

    lesscEntryPoints in Compile <<= baseDirectory(customLessEntryPoints)

Build.scala example:

    lazy val main = play.Project(appName, appVersion, mainDeps).settings(lesscSettings: _*).settings(
      lessEntryPoints := Nil,
      lesscEntryPoints in Compile <<= baseDirectory(customLessEntryPoints)
    )

Less command line [options][lessoptions] can be passed in using `lesscOptions`:

    lesscOptions in Compile := Seq("--no-color", "--yui-compress")

The `--verbose` option outputs each `lessc` command into the console:

    lesscOptions in Compile := Seq("--no-color", "--verbose")

A directory value is required for a non global copy of lessc (trailing slash optional):

    lesscOptions in Compile := Seq("dir=node_modules/.bin")

## Changelog

0.1.1 - October 4, 2013

- Updated to work with Play 2.2.x.

0.1.0 - September 2, 2013

- Windows works now with a global or local copy of lessc using lessc.cmd as a fallback.

0.0.9 - August 31, 2013

- Added fallback to Play's LessCompiler if the lessc command is not found.
- New directory configuration option for a local copy of lessc.
- Only the normal LessCompiler works on Heroku. The lessc command is not available at compile time even if using a multipack with installing Node before scala.

0.0.8 - July 23, 2013

- Fixed regex issue with dependencies having leading spaces.

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
[less]: http://lesscss.org/
[lessoptions]: https://github.com/less/less.js/wiki/Command-Line-Usage