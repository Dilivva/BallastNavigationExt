import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMpp)
    alias(libs.plugins.androidLib)
    id("com.vanniktech.maven.publish").version("0.25.3")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
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
            baseName = "annotation"
        }
    }

    sourceSets {

    }
}

android {
    namespace = "com.dilivva.ballastnavigationext.annotation"
    compileSdk = 34
    defaultConfig {
        minSdk = 23
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)
    val versionFile = project.rootProject.file("version")
    val versionTxt = versionFile.readText().trimEnd()
    val isDev = findProperty("env")?.equals("dev") ?: false
    val version = if (isDev) versionTxt.plus("-SNAPSHOT") else versionTxt

    coordinates("com.dilivva.ballastnavigationext", "annotation", version)

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