name := "scalajs root project"

scalaVersion := "2.12.1"
 
lazy val root = project.in(file(".")).
  aggregate(scalajodamoneyJS, scalajodamoneyJVM).
  settings(
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )

lazy val scalajodamoney = crossProject.in(file(".")).
  settings(
    name := "scala-joda-money",
    organization            := "io.github.er1c",
    sonatypeProfileName     := "io.github.er1c",
    version                 := "0.1",
    publishMavenStyle       := true,
    scalaVersion            := "2.12.1",
    exportJars              := true,
    publishMavenStyle       := true,
    publishArtifact in Test := false,
    publishTo           := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra :=
      <url>https://github.com/er1c/scala-joda-money</url>
      <licenses>
        <license>
          <name>Apache 2.0</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:er1c/scala-java-locales.git</url>
        <connection>scm:git:git@github.com:er1c/scala-java-locales.git</connection>
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
    ,
    pomIncludeRepository := { _ => false },
    parallelExecution in Test := false // TODO: There's some concurrency problem at least in the TestMoneyFormatterBuilder or how the TestNGSuite is setup...
  ).
  jvmSettings(
    // Add JVM-specific settings here
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.1",
      "org.testng" % "testng" % "6.11"
    )
  ).
  jsSettings(
    // Add JS-specific settings here
    libraryDependencies ++= Seq(
		  "io.github.cquiroz" %%% "scala-java-locales" % "0.5.0-cldr30"
		)
  )

lazy val scalajodamoneyJVM = scalajodamoney.jvm
lazy val scalajodamoneyJS = scalajodamoney.js
