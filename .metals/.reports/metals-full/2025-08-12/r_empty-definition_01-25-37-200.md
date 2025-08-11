error id: file://<WORKSPACE>/build.sbt:`<none>`.
file://<WORKSPACE>/build.sbt
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -sys/props/getOrElse.
	 -sys/props/getOrElse#
	 -sys/props/getOrElse().
	 -scala/Predef.sys.props.getOrElse.
	 -scala/Predef.sys.props.getOrElse#
	 -scala/Predef.sys.props.getOrElse().
offset: 358
uri: file://<WORKSPACE>/build.sbt
text:
```scala
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "W9-13Practical",

    // --- Pick the correct JavaFX native classifier for your OS/CPU ---
    libraryDependencies ++= {
      val os   = sys.props.getOrElse("os.name", "").toLowerCase
      val arch = sys.props.getO@@rElse("os.arch", "").toLowerCase

      val classifier =
        if (os.contains("mac") && (arch.contains("aarch64") || arch.contains("arm64"))) "mac-aarch64"
        else if (os.contains("mac")) "mac"
        else if (os.contains("win") && (arch.contains("aarch64") || arch.contains("arm64"))) "win-aarch64"
        else if (os.contains("win")) "win"
        else if (os.contains("linux") && (arch.contains("aarch64") || arch.contains("arm64"))) "linux-aarch64"
        else if (os.contains("linux")) "linux"
        else throw new Exception(s"Unknown platform: $os / $arch")

      val fxVersion = "21.0.5"

      // Minimal set (uncomment what you need)
      val fxModules = Seq("base", "graphics", "controls" /*, "fxml", "media", "swing", "web"*/)

      fxModules.map(m => "org.openjfx" % s"javafx-$m" % fxVersion classifier classifier)
    },

    // ScalaFX 21 for Scala 3
    libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32",

    // Your DB libraries (unchanged)
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc" % "4.3.0",
      "com.h2database" % "h2" % "2.2.224",
      "org.apache.derby" % "derby" % "10.17.1.0",
      "org.apache.derby" % "derbytools" % "10.17.1.0"
    )
  )

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.