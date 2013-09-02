name := "play-lessc"

version := "0.1.0"

sbtPlugin := true

organization := "com.jmparsons"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("play" % "sbt-plugin" % "2.1.3")

publishTo := Some(Resolver.file("file", new File( "/tmp/play-lessc" )))