@file:Suppress("UnstableApiUsage","unused")

import com.vanniktech.maven.publish.*
plugins {
    alias(libs.plugins.kotlinMpp)
    alias(libs.plugins.androidLib)
    alias(libs.plugins.composeMultiplatform)
    id("com.vanniktech.maven.publish").version("0.25.3")
    id("com.sherepenko.gradle.plugin-build-version").version("0.3.0")
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
                implementation(libs.turbine)
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

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)
    val versionFile = project.rootProject.file("version")
    val versionTxt = versionFile.readText().trimEnd()
    val isDev = findProperty("env")?.equals("dev") ?: false
    val version = if (isDev) versionTxt.plus("-SNAPSHOT") else versionTxt

    coordinates("com.dilivva.ballastnavigationext", "ballastnavigationext", version)

    pom{
        name.set("Ballast Navigation Extension")
        description.set("An opinionated fast Ballast Navigation set-up for Compose multiplatform.")
        inceptionYear.set("2023")
        url.set("https://github.com/Dilivva/BallastNavigationExt")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/Dilivva/BallastNavigationExt/LICENSE")
                distribution.set("https://github.com/Dilivva/BallastNavigationExt/LICENSE")
            }
        }
        developers {
            developer {
                id.set("Dilivva")
                name.set("dilivva")
                url.set("https://github.com/Dilivva/")
            }
        }
        scm {
            url.set("https://github.com/Dilivva/BallastNavigationExt/")
            connection.set("scm:git:git://github.com/Dilivva/BallastNavigationExt.git")
            developerConnection.set("scm:git:ssh://git@github.com/Dilivva/BallastNavigationExt.git")
        }
    }

    signAllPublications()
}