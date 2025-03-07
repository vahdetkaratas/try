plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.nfctron.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nfctron.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":shared"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.tooling)
    implementation(libs.compose.navigation)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)

    // SQLDelight
    implementation(libs.sqldelight.android)
    implementation(libs.sqldelight.runtime)

    // Moko Resources
    implementation(libs.moko.resources)

    // Accompanist
    implementation(libs.accompanist.swiperefresh)
}