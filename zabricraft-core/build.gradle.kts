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
            name.set("zabricraft-core")
            description.set("Core plugin of the ZabriCraft ecosystem.")
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
    implementation("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    
    api(project(":zabricraft-runtime"))

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks.test {
    useJUnitPlatform()
}
