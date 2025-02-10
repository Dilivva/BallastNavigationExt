import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMpp)
    id("com.vanniktech.maven.publish").version("0.25.3")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.kotlin.poet)
                implementation(libs.kotlin.poet.ksp)
                implementation(libs.ksp.dev)
                if (findProperty("env") != null){
                    val versionFile = project.rootProject.file("version")
                    val versionTxt = versionFile.readText().trimEnd()
                    val isDev = findProperty("env")?.equals("dev") ?: false
                    val version = if (isDev) versionTxt.plus("-SNAPSHOT") else versionTxt
                    implementation("com.dilivva.ballastnavigationext:annotation:$version")
                }else {
                    implementation(projects.annotation)
                }
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)
    val versionFile = project.rootProject.file("version")
    val versionTxt = versionFile.readText().trimEnd()
    val isDev = findProperty("env")?.equals("dev") ?: false
    val version = if (isDev) versionTxt.plus("-SNAPSHOT") else versionTxt

    coordinates("com.dilivva.ballastnavigationext", "processor", version)

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