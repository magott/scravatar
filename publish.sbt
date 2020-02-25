disablePlugins(aether.AetherPlugin)
enablePlugins(aether.SignedAetherPlugin)

overridePublishSignedSettings
overridePublishLocalSettings

publishTo := {
  if (isSnapshot.value) {
    Some(Opts.resolver.sonatypeSnapshots)
  } else {
    Some(Opts.resolver.sonatypeStaging)
  }
}

pomIncludeRepository := { x =>
  false
}

packageOptions += {
  val title  = name.value
  val ver    = version.value
  val vendor = organization.value

  Package.ManifestAttributes(
    "Created-By"               -> "Scala Build Tool",
    "Built-By"                 -> System.getProperty("user.name"),
    "Build-Jdk"                -> System.getProperty("java.version"),
    "Specification-Title"      -> title,
    "Specification-Version"    -> ver,
    "Specification-Vendor"     -> vendor,
    "Implementation-Title"     -> title,
    "Implementation-Version"   -> ver,
    "Implementation-Vendor-Id" -> vendor,
    "Implementation-Vendor"    -> vendor
  )
}

credentials ++= Seq(
  Credentials(Path.userHome / ".sbt" / ".sonatype-credentials"),
)

homepage := Some(url("https://github.com/magott/scravatar"))

startYear := Some(2017)

licenses := Seq(
  "Apache2" -> url("https://github.com/magott/scravatar/blob/master/LICENSE")
)

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

scmInfo := Some(
  ScmInfo(
    new URL("https://github.com/magott/scravatar"),
    "scm:git:git@github.com:magott/scravatar.git",
    Some("scm:git:git@github.com:magott/scravatar.git")
  ))

developers ++= List(
  Developer(
    "magott",
    "Morten Andersen-Gott",
    "",
    null
  ),

)