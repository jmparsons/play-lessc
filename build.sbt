name := "play-lessc"

version := "0.1.2"

sbtPlugin := true

organization := "com.jmparsons"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

publishTo := Some(Resolver.url("sbt-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns))

publishMavenStyle := false

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

crossPaths := false

pomExtra := (
  <url>http://github.com/jmparsons/play-lessc</url>
  <licenses>
    <license>
      <name>MIT License</name>
      <url>http://jmparsons.mit-license.org/</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:jmparsons/play-lessc.git</url>
    <connection>scm:git:git@github.com:jmparsons/play-lessc.git</connection>
  </scm>
  <developers>
    <developer>
      <id>jmparsons</id>
      <name>Jonathan Parsons</name>
      <url>https://github.com/jmparsons</url>
    </developer>
  </developers>
)
