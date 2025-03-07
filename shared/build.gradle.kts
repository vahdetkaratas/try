plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    // Remove iOS targets for now
    // listOf(
    //     iosX64(),
    //     iosArm64(),
    //     iosSimulatorArm64()
    // ).forEach {
    //     it.binaries.framework {
    //         baseName = "shared"
    //     }
    // }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.json)
                implementation("io.ktor:ktor-client-json:${libs.versions.ktor.get()}")
                implementation("io.ktor:ktor-client-logging:${libs.versions.ktor.get()}")

                // Koin
                implementation(libs.koin.core)

                // Coroutines
                implementation(libs.coroutines.core)

                // SQLDelight
                implementation(libs.sqldelight.runtime)

                // Serialization
                implementation(libs.serialization.json)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.sqldelight.android)
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.mockk:mockk:1.13.8")
            }
        }
        
        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("io.mockk:mockk-android:1.13.8")
            }
        }
    }
}

android {
    namespace = "com.nfctron.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    databases {
        create("CryptocurrencyDatabase") {
            packageName.set("com.nfctron.db")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            migrationOutputDirectory.set(file("src/commonMain/sqldelight/migrations"))
            verifyMigrations.set(false)
        }
    }
}