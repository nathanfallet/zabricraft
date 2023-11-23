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
includeBuild("convention-plugins")

include(":zabricraft-usecases")
include(":zabricraft-datetime")
include(":zabricraft-bcrypt")
include(":zabricraft-exposed")
include(":zabricraft-mysql")
include(":zabricraft-h2")
include(":zabricraft-koin")
include(":zabricraft-runtime")

include(":zabricraft-core")
