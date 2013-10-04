name := "play-lessc"

version := "0.1.1"

sbtPlugin := true

organization := "com.jmparsons"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

publishTo := Some(Resolver.file("file", new File( "/tmp/play-lessc" )))