import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.plugin.serialization)
  alias(libs.plugins.intellij)
  alias(libs.plugins.buildConfig)
}

group = "com.github.jpuf.plugin"

intellijPlatform {
  pluginConfiguration {
    vendor {
      name = "JPUF"
      url = "https://github.com/jpuf/"
      email = "email@me.com"
    }
  }
}

buildConfig {
  packageName("foundry.intellij.skate")
  buildConfigField("String", "VERSION", "\"${project.property("VERSION_NAME")}\"")
  useKotlinOutput {
    topLevelConstants = true
    internalVisibility = true
  }
}

// TODO reconcile exclusions and this by figuring out which configurations need to exclude
//  coroutines. https://youtrack.jetbrains.com/issue/IJPL-163489
configurations.configureEach {
  // Do not bring in Material (we use Jewel)
  exclude(group = "org.jetbrains.compose.material")
  // Do not bring Coroutines or slf4j (the IDE has its own)
  exclude(group = "org.slf4j")
}

val exclusions: Action<ModuleDependency> = Action {
  // Do not bring in Material (we use Jewel)
  exclude(group = "org.jetbrains.compose.material")
  // Do not bring Coroutines or slf4j (the IDE has its own)
  exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
  exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core-jvm")
  exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-bom")
  exclude(group = "org.slf4j")
}

configurations
  .named { it.endsWith("ForLint") }
  .configureEach { attributes { attribute(KotlinPlatformType.attribute, KotlinPlatformType.jvm) } }

dependencies {
  intellijPlatform {
    // https://plugins.jetbrains.com/docs/intellij/android-studio.html#open-source-plugins-for-android-studio
    // https://plugins.jetbrains.com/docs/intellij/android-studio-releases-list.html
    // https://plugins.jetbrains.com/plugin/22989-android/versions/stable
    plugin("org.jetbrains.android:242.21829.142")
    bundledPlugins(
      "com.intellij.java",
      "org.intellij.plugins.markdown",
      "org.jetbrains.plugins.terminal",
      "org.jetbrains.kotlin",
    )

    pluginVerifier()
    zipSigner()
    instrumentationTools()

    testFramework(TestFrameworkType.Platform)
    testFramework(TestFrameworkType.Bundled)
  }

  implementation(projects.compose, exclusions)

  implementation(libs.kaml)
  implementation(libs.kotlinx.serialization.core)
  implementation(libs.okhttp)
  implementation(libs.okhttp.loggingInterceptor)

  compileOnly(libs.coroutines.core.ij)

  testImplementation(libs.junit)
  testImplementation(libs.truth)
}
