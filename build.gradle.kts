plugins {
    id("java")
    application
}

group = "io.github.pixeldev"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
  mainClass.set("io.github.pixeldev.imageprocessing.Bootstrap")
}

tasks.withType<Jar> {
  manifest {
    attributes["Main-Class"] = "io.github.pixeldev.imageprocessing.Bootstrap"
  }
}
