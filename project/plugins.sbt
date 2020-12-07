// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.2")


//addSbtPlugin("de.johoop" % "jacoco4sbt" % "1.2.4")
addSbtPlugin("com.github.sbt" % "sbt-jacoco" % "3.2.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-coffeescript" % "1.0.2")

// plugins.sbt
val jacocoVersion = "0.8.5"

dependencyOverrides ++= Seq(
  "org.jacoco" % "org.jacoco.core" % jacocoVersion,
  "org.jacoco" % "org.jacoco.report" % jacocoVersion,
  "org.jacoco" % "org.jacoco.agent" % jacocoVersion)
