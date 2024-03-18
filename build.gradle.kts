plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApp).apply(false)
    alias(libs.plugins.androidLib).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMpp).apply(false)
    alias(libs.plugins.composeMultiplatform).apply(false)
    alias(libs.plugins.kspPlugin).apply(false)
    id("com.sherepenko.gradle.plugin-build-version").version("0.3.0")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}