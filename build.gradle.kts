plugins {
    id("java")
}

group = "io.github.pixeldev"
version = "0.0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}