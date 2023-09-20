plugins {
    alias(libs.plugins.androidApp)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.dilivva.ballastnavigationex"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.dilivva.ballastnavigationex"
        minSdk = 25
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.compose.uitooling)
    implementation(libs.compose.preview)
}