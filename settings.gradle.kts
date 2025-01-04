@file:Suppress("UnstableApiUsage")

import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

pluginManagement {

    repositories {
        mavenCentral()
        google()
        maven("https://packages.jetbrains.team/maven/p/kpm/public/")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
    }
}

plugins {
    id("org.jetbrains.intellij.platform.settings") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.1.0" apply false
}

dependencyResolutionManagement {
    versionCatalogs {
        if (System.getenv("DEP_OVERRIDES") == "true") {
            val overrides = System.getenv().filterKeys { it.startsWith("DEP_OVERRIDE_") }
            maybeCreate("libs").apply {
                for ((key, value) in overrides) {
                    val catalogKey = key.removePrefix("DEP_OVERRIDE_").lowercase()
                    println("Overriding $catalogKey with $value")
                    version(catalogKey, value)
                }
            }
        }
    }

    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

    repositories {
        mavenCentral()
        google()
        maven("https://packages.jetbrains.team/maven/p/kpm/public/")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        intellijPlatform { defaultRepositories() }
        gradlePluginPortal()
    }
}

rootProject.name = "module-wizard"

include(
    ":plugin",
    ":compose",
    ":model"
)

// https://docs.gradle.org/5.6/userguide/groovy_plugin.html#sec:groovy_compilation_avoidance
enableFeaturePreview("GROOVY_COMPILATION_AVOIDANCE")

// https://docs.gradle.org/current/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")



