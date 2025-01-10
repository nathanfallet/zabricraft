pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "zabricraft"
include(":zabricraft-kaccelero")
include(":zabricraft-mysql")
include(":zabricraft-h2")
include(":zabricraft-koin")
include(":zabricraft-runtime")

include(":zabricraft-core")
