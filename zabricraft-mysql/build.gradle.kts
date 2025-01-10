plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.vanniktech.maven.publish")
    id("org.jetbrains.kotlinx.kover")
    id("com.google.devtools.ksp")
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    pom {
        name.set("zabricraft-mysql")
        description.set("Wrapping mysql-connector-j as a plugin.")
        url.set(project.ext.get("url")?.toString())
        licenses {
            license {
                name.set(project.ext.get("license.name")?.toString())
                url.set(project.ext.get("license.url")?.toString())
            }
        }
        developers {
            developer {
                id.set(project.ext.get("developer.id")?.toString())
                name.set(project.ext.get("developer.name")?.toString())
                email.set(project.ext.get("developer.email")?.toString())
                url.set(project.ext.get("developer.url")?.toString())
            }
        }
        scm {
            url.set(project.ext.get("scm.url")?.toString())
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    api("com.mysql:mysql-connector-j:8.0.33")
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
