pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Plugins
            version("kotlin", "2.0.20")
            version("agp", "8.5.0")
            plugin("jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("kover", "org.jetbrains.kotlinx.kover").version("0.8.3")
            plugin("ksp", "com.google.devtools.ksp").version("2.0.20-1.0.24")
            plugin("maven", "com.vanniktech.maven.publish").version("0.28.0")

            // Kaccelero
            version("kaccelero", "0.4.3")
            library("kaccelero-core", "dev.kaccelero", "core").versionRef("kaccelero")
            library("kaccelero-i18n", "dev.kaccelero", "i18n").versionRef("kaccelero")
            library("kaccelero-auth", "dev.kaccelero", "auth").versionRef("kaccelero")
            library("kaccelero-database-exposed", "dev.kaccelero", "database-exposed").versionRef("kaccelero")
            library("kaccelero-routers-client", "dev.kaccelero", "routers-client-ktor").versionRef("kaccelero")
            bundle(
                "kaccelero",
                listOf(
                    "kaccelero-core",
                    "kaccelero-i18n",
                    "kaccelero-auth",
                    "kaccelero-database-exposed",
                    "kaccelero-routers-client",
                )
            )

            // Tests
            library("tests-mockk", "io.mockk:mockk:1.13.12")

            // Ktor
            version("ktor", "2.3.12")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-apache", "io.ktor", "ktor-client-apache").versionRef("ktor")
            library("ktor-client-jetty", "io.ktor", "ktor-client-jetty").versionRef("ktor")
            library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor-client-mock", "io.ktor", "ktor-client-mock").versionRef("ktor")
            bundle(
                "ktor-client-api",
                listOf(
                    "ktor-client-core",
                    "ktor-client-apache",
                    "ktor-client-jetty",
                    "ktor-client-content-negotiation",
                    "ktor-serialization-kotlinx-json"
                )
            )
            bundle(
                "ktor-client-tests",
                listOf(
                    "ktor-client-mock",
                )
            )

            // Koin
            version("koin", "3.5.0")
            library("koin-core", "io.insert-koin", "koin-core").versionRef("koin")
            library("koin-ktor", "io.insert-koin", "koin-ktor").versionRef("koin")

            // Others
            library("spigot", "org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
            library("mysql", "com.mysql:mysql-connector-j:8.0.33")
            library("h2", "com.h2database:h2:2.3.232")
        }
    }
}

rootProject.name = "zabricraft"
include(":kaccelero")
include(":mysql")
include(":h2")
include(":koin")
include(":ktor")
include(":runtime")

include(":core")
