plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.moko.resources) apply false
}

// No dependencies here

buildscript {
    // Dependencies for the build script itself
    dependencies {
        classpath("app.cash.sqldelight:gradle-plugin:2.0.0")
    }
}