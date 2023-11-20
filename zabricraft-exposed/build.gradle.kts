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
            name.set("zabricraft-exposed")
            description.set("Wrapping JetBrains/Exposed as a plugin.")
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
    api("org.jetbrains.exposed:exposed-core:0.44.1")
    api("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    api("org.jetbrains.exposed:exposed-dao:0.44.1")
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
