plugins {
    alias(libs.plugins.jvm) apply false
}

allprojects {
    group = "me.nathanfallet.zabricraft"
    version = "0.3.0"
    project.ext.set("url", "https://github.com/nathanfallet/zabricraft")
    project.ext.set("license.name", "GPL-3.0")
    project.ext.set("license.url", "https://opensource.org/licenses/GPL-3.0")
    project.ext.set("developer.id", "nathanfallet")
    project.ext.set("developer.name", "Nathan Fallet")
    project.ext.set("developer.email", "contact@nathanfallet.me")
    project.ext.set("developer.url", "https://www.nathanfallet.me")
    project.ext.set("scm.url", "https://github.com/nathanfallet/zabricraft.git")

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        configurations
            .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
            .forEach {
                add(it.name, "io.mockative:mockative-processor:2.0.1")
            }
    }
}
