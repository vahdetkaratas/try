plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.moko.resources)
    id("com.android.library")
}

android {
    namespace = "com.nfctron.app.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}

kotlin {
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Ktor
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization)

                // Koin
                implementation(libs.koin.core)

                // SQLDelight
                implementation(libs.sqldelight.runtime)

                // Serialization
                implementation(libs.serialization.json)

                // Resources
                implementation(libs.moko.resources)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.android)
                implementation(libs.ktor.client.android)
            }
        }
    }
}