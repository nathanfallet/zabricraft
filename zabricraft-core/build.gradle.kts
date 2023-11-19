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

kotlin {
    jvmToolchain(19)
}
