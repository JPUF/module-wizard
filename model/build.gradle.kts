plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.buildConfig)
}

kotlin {
    jvm()
    sourceSets {
        jvmMain {
            dependencies {

            }
        }
    }
}