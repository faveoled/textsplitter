enablePlugins(ScalaJSPlugin)
enablePlugins(ScalablyTypedConverterPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "app"
scalaVersion := "3.2.2"
externalNpm := baseDirectory.value

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

// ECMAScript
scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

Compile / npmDependencies ++= Seq(
    "jszip" -> "3.10.1"
  )

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.3.0"

// Add support for the DOM in `run` and `test`
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

// uTest settings
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.8.1" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")

