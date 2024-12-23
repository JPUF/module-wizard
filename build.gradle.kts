@file:Suppress("UnstableApiUsage")

import com.github.gmazzo.buildconfig.BuildConfigExtension
import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.Companion.DEFAULT
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.jpuf.modulewizardpoc"
version = "1.0-SNAPSHOT"

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.plugin.sam)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.versionsPlugin)
    alias(libs.plugins.intellij) apply false
    alias(libs.plugins.buildConfig) apply false
    alias(libs.plugins.binaryCompatibilityValidator)
}

buildscript {
    dependencies {
        // Apply boms for buildscript classpath
        classpath(platform(libs.asm.bom))
        classpath(platform(libs.kotlin.bom))
        classpath(platform(libs.coroutines.bom))
        classpath(platform(libs.kotlin.gradlePlugins.bom))
    }
}

val kotlinVersion = libs.versions.kotlin.get()

val jvmTargetVersion = libs.versions.jvmTarget.map(JvmTarget::fromTarget)

subprojects {
    project.pluginManager.withPlugin("com.github.gmazzo.buildconfig") {
        configure<BuildConfigExtension> {
            buildConfigField("String", "KOTLIN_VERSION", "\"$kotlinVersion\"")
        }
    }

    pluginManager.withPlugin("java") {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(
                    JavaLanguageVersion.of(libs.versions.jdk.get().removeSuffix("-ea").toInt())
                )
            }
        }

        tasks.withType<JavaCompile>().configureEach {
            options.release.set(libs.versions.jvmTarget.map(String::toInt))
        }
    }

    val isForIntelliJPlugin = project.hasProperty("INTELLIJ_PLUGIN")
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            if (!isForIntelliJPlugin) {
                explicitApi()
            }
        }
    }

    plugins.withType<KotlinBasePlugin>().configureEach {
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                val kotlinVersion =
                    if (isForIntelliJPlugin) {
                        KOTLIN_1_9
                    } else {
                        DEFAULT
                    }
                languageVersion.set(kotlinVersion)
                apiVersion.set(kotlinVersion)

                if (kotlinVersion != DEFAULT) {
                    // Gradle/IntelliJ forces a lower version of kotlin, which results in warnings that
                    // prevent use of this sometimes.
                    // https://github.com/gradle/gradle/issues/16345
                    allWarningsAsErrors.set(false)
                } else {
                    allWarningsAsErrors.set(true)
                }
                this.jvmTarget.set(jvmTargetVersion)
                freeCompilerArgs.addAll(
                    // Enhance not null annotated type parameter's types to definitely not null types
                    // (@NotNull T => T & Any)
                    "-Xenhance-type-parameter-types-to-def-not-null",
                    // Support inferring type arguments based on only self upper bounds of the corresponding
                    // type parameters
                    "-Xself-upper-bound-inference",
                    "-Xjsr305=strict",
                    // Match JVM assertion behavior:
                    // https://publicobject.com/2019/11/18/kotlins-assert-is-not-like-javas-assert/
                    "-Xassertions=jvm",
                    // Potentially useful for static analysis tools or annotation processors.
                    "-Xemit-jvm-type-annotations",
                    // Enable new jvm-default behavior
                    // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
                    "-Xjvm-default=all",
                    "-Xtype-enhancement-improvements-strict-mode",
                    // https://kotlinlang.org/docs/whatsnew1520.html#support-for-jspecify-nullness-annotations
                    "-Xjspecify-annotations=strict",
                )
                // https://jakewharton.com/kotlins-jdk-release-compatibility-flag/
                freeCompilerArgs.add(jvmTargetVersion.map { "-Xjdk-release=${it.target}" })
                optIn.addAll(
                    "kotlin.contracts.ExperimentalContracts",
                    "kotlin.experimental.ExperimentalTypeInference",
                    "kotlin.ExperimentalStdlibApi",
                    "kotlin.time.ExperimentalTime",
                )
            }
        }
    }

    if (isForIntelliJPlugin) {
        project.pluginManager.withPlugin("org.jetbrains.intellij.platform") {
            data class PluginDetails(
                val pluginId: String,
                val name: String,
                val description: String,
                val version: String,
                val sinceBuild: String
            )

            val pluginDetails =
                PluginDetails(
                    pluginId = property("PLUGIN_ID").toString(),
                    name = property("PLUGIN_NAME").toString(),
                    description = property("PLUGIN_DESCRIPTION").toString(),
                    version = property("VERSION_NAME").toString(),
                    sinceBuild = property("PLUGIN_SINCE_BUILD").toString()
                )

            configure<IntelliJPlatformExtension> {
                pluginConfiguration {
                    id.set(pluginDetails.pluginId)
                    name.set(pluginDetails.name)
                    version.set(pluginDetails.version)
                    description.set(pluginDetails.description)
                    ideaVersion {
                        sinceBuild.set(pluginDetails.sinceBuild)
                        untilBuild.set(project.provider { null })
                    }
                }
            }
            project.dependencies {
                configure<IntelliJPlatformDependenciesExtension> { intellijIdeaCommunity("2024.2.1") }
            }
        }
    }
}
