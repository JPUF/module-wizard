plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.buildConfig)
  alias(libs.plugins.compose)
  alias(libs.plugins.kotlin.plugin.compose)
}

kotlin {
  jvm()

  sourceSets {
    jvmMain {
      dependencies {
        implementation(projects.model)
        implementation(compose.animation)
        implementation(compose.components.resources)
        implementation(compose.desktop.common)
        implementation(compose.desktop.linux_arm64)
        implementation(compose.desktop.linux_x64)
        implementation(compose.desktop.macos_arm64)
        implementation(compose.desktop.macos_x64)
        implementation(compose.desktop.windows_x64)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.material3)
        implementation(compose.ui)
        implementation(libs.compose.markdown)
        implementation(libs.jewel.bridge)
        implementation(libs.kotlin.poet)
        implementation(libs.markdown)
      }
    }
    jvmTest { dependencies { implementation(libs.junit) } }
  }
}
