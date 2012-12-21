name := "scravatar"

scalaVersion:="2.10.0"

crossScalaVersions := Seq("2.9.1", "2.9.2", "2.10.0")

organization := "com.andersen-gott"

version := "1.0.2"

libraryDependencies <++= (scalaVersion) { s =>
  Seq(
    "org.scalatest" % ("scalatest_" + s) % "1.8" % "test"
  )
}

credentials += Credentials(Path.userHome / ".sbt" / "magott-credentials")

useGpg:=true

publishTo <<= (version) { version: String =>
    if (version.trim.endsWith("SNAPSHOT"))
      Some("Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
    else
      Some("Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

seq(aetherPublishSettings: _*)

homepage := Some(new URL("http://github.com/magott/scravatar"))

startYear := Some(2012)

licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))

pomExtra <<= (pomExtra, name, description) {(pom, name, desc) => pom ++ xml.Group(
  <scm>
    <url>http://github.com/magott/scravatar</url>
  	<connection>scm:git:git://github.com/magott/scravatar.git</connection>
  	<developerConnection>scm:git:git@github.com:magott/scravatar.git</developerConnection>
  </scm>
  <developers>
    <developer>
      <id>magott</id>
  	  <name>Morten Andersen-Gott</name>
  	  <url>http://www.andersen-gott.com</url>
  	</developer>
  </developers>
)}
