plugins {
    alias(libs.plugins.kotlinMpp)
    alias(libs.plugins.androidLib)
    alias(libs.plugins.composeMultiplatform)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "BallastNavigationExt"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                api(libs.ballast.core)
                api(libs.ballast.navigation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation(compose.animation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val androidMain by getting{
            dependencies{
                implementation(libs.activity.compose)
            }
        }
    }
}

android {
    namespace = "com.dilivva.ballastnavigationext"
    compileSdk = 33
    defaultConfig {
        minSdk = 25
    }
}