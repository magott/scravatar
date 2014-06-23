## Gravatar - because I shouldn't have to upload a picture of myself in a sombrero more than once...

### API
Simple

    Gravatar("you@example.com").avatarUrl

To get a secure URL (for embedding on websites served over HTTPS/SSL)

    Gravatar("you@example.com").ssl(true).avatarUrl

Setting all the properties

    Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl

More info
[at Gravatar](http://gravatar.com/site/implement/images/)


### Adding the Scravatar dependency

Scravatar does not depend on any third party frameworks. It is built and deployed on maven central. To use it with sbt add

    libraryDependencies ++=
	  Seq(
	    "com.andersen-gott" %% "scravatar" % "1.0.3"
	  )

to your build file

With Maven

    <dependency>
	  <groupId>com.andersen-gott</groupId>
	  <artifactId>scravatar_2.11</artifactId>
	  <version>1.0.3</version>
	</dependency>