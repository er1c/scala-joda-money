name := "scalajs root project"
scalaVersion := "2.11.8"

 
lazy val root = project.in(file(".")).
  aggregate(scalajodamoneyJS, scalajodamoneyJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val scalajodamoney = crossProject.in(file(".")).
  settings(
    name := "scala-joda-money",
    organization := "com.eluvio",
    version := "0.1-SNAPSHOT",
    publishMavenStyle := true,
    scalaVersion := "2.11.8"
  ).
  jvmSettings(
    // Add JVM-specific settings here
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.0",
      "org.testng" % "testng" % "6.9.13.6"
    )
  ).
  jsSettings(
    // Add JS-specific settings here
    libraryDependencies ++= Seq(
		  "com.github.cquiroz" %%% "scala-java-locales" % "0.3.1-cldr30"
		)
  )

lazy val scalajodamoneyJVM = scalajodamoney.jvm
lazy val scalajodamoneyJS = scalajodamoney.js
 

lazy val pomData =
  <scm>
    <url>git@github.com:eluvio/scala-joda-money.git</url>
    <connection>eluvio:git:git@github.com:eluvio/scala-joda-money.git</connection>
  </scm>
  <developers>
    <developer>
      <id>er1c</id>
      <name>Eric Peters</name>
      <url>https://github.com/er1c</url>
      <roles>
        <role>Project Lead (current Scala version)</role>
      </roles>
    </developer>
    <developer>
      <id>jodastephen</id>
      <name>Stephen Colebourne</name>
      <url>https://github.com/jodastephen</url>
      <roles>
        <role>Project Lead (original Java implementation)</role>
      </roles>
   </developer>
  </developers>