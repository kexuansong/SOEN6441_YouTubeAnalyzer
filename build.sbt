lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .settings(
    name := """play-java-starter-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
//      // Test Database
//      "com.h2database" % "h2" % "1.4.199",
      // Testing libraries for dealing with CompletionStage...
      "org.assertj" % "assertj-core" % "3.14.0" % Test,
      "org.awaitility" % "awaitility" % "4.0.1" % Test,
    ),
    libraryDependencies += "com.google.apis" % "google-api-services-youtube" % "v3-rev222-1.25.0",
    javacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-parameters",
      "-Xlint:unchecked",
      "-Xlint:deprecation",
      "-Werror"
    ),

    libraryDependencies += "com.google.api-client" % "google-api-client-extensions" % "1.6.0-beta",
    libraryDependencies += "com.google.api-client" % "google-api-client-java6" % "1.30.10",
    libraryDependencies += "com.google.oauth-client" % "google-oauth-client-jetty" % "1.31.1",
    libraryDependencies += "com.google.guava" % "guava" % "30.0-jre",
    libraryDependencies += "org.json" % "json" % "20200518",
    libraryDependencies += "com.vdurmont" % "emoji-java" % "5.1.1",
    libraryDependencies += "org.mockito" % "mockito-core" % "3.3.3",
    libraryDependencies += "org.mockito" % "mockito-inline" % "2.7.13" % Test,
    libraryDependencies += "com.googlecode.json-simple" % "json-simple" % "1.1.1",
    libraryDependencies += "com.google.code.gson" % "gson" % "2.8.6",
    libraryDependencies += "org.webjars" % "jquery" % "3.3.1",
    libraryDependencies += "javax.websocket" % "javax.websocket-api" % "1.1",
    libraryDependencies += "javax.json" % "javax.json-api" % "1.1.4",
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.5"% Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.5",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.5",

      libraryDependencies ++= Seq(
        ws,
      ehcache
    ),


      javaOptions in Test ++= Seq(

      "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9998",
      "-Xms512M",
      "-Xmx1536M",
      "-Xss1M",
      "-XX:MaxPermSize=384M"
    ),


//    // Make verbose tests
//    testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
  )