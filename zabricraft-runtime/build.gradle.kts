plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("convention.publication")
    id("org.jetbrains.kotlinx.kover")
    id("com.google.devtools.ksp")
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("zabricraft-runtime")
            description.set("Wrapping all dependencies as a plugin.")
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(19)
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    api(project(":zabricraft-usecases"))
    api(project(":zabricraft-i18n"))
    api(project(":zabricraft-bcrypt"))
    api(project(":zabricraft-surexposed"))
    api(project(":zabricraft-mysql"))
    api(project(":zabricraft-h2"))
    api(project(":zabricraft-koin"))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
    from(
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory()) it else zipTree(it)
        }
    )
}
