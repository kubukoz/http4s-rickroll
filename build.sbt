inThisBuild(
  List(
    organization := "com.kubukoz",
    homepage := Some(url("https://github.com/kubukoz/http4s-rickroll")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "kubukoz",
        "Jakub Kozłowski",
        "kubukoz@gmail.com",
        url("https://kubukoz.com")
      )
    )
  )
)

def crossPlugin(x: sbt.librarymanagement.ModuleID) = compilerPlugin(x.cross(CrossVersion.full))

val compilerPlugins = List(
  crossPlugin("org.typelevel" % "kind-projector" % "0.11.0"),
  crossPlugin("com.github.cb372" % "scala-typed-holes" % "0.1.3"),
  compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
)

val commonSettings = Seq(
  scalaVersion := "2.13.2",
  scalacOptions --= Seq("-Xfatal-warnings"),
  name := "http4s-rickroll",
  updateOptions := updateOptions.value.withGigahorse(false),
  libraryDependencies ++= Seq(
    "org.http4s" %% "http4s-core" % "0.21.4",
    "org.http4s" %% "http4s-dsl" % "0.21.4",
    "org.http4s" %% "http4s-blaze-server" % "0.21.4",
    "org.scalatest" %% "scalatest" % "3.1.0" % Test
  ) ++ compilerPlugins
)

val http4sRickroll =
  project.in(file(".")).settings(commonSettings)
