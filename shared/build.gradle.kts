plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
    id("com.codingfeline.buildkonfig") version "+"
    id("org.jetbrains.kotlinx.kover") version "0.9.3"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            implementation(libs.supabase)
            implementation(libs.supabase.functions)

        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
        }
    }
}

android {
    namespace = "com.ivangarzab.bookclub.shared"
    //noinspection GradleDependency
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

buildkonfig {
    packageName = "com.ivangarzab.bookclub.shared"

    defaultConfigs {
        // Production Supabase credentials
        val supabaseUrl: String = properties["SUPABASE_URL"] as String
        val supabaseKey: String = properties["SUPABASE_KEY"] as String
        require(supabaseUrl.isNotEmpty() && supabaseKey.isNotEmpty()) {
            "Make sure to provide the SUPABASE_URL and SUPABASE_KEY in your global gradle.properties file."
        }
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "SUPABASE_KEY",
            supabaseKey
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "SUPABASE_URL",
            supabaseUrl
        )

        // Testing Supabase credentials
        val testSupabaseUrl: String = properties["TEST_SUPABASE_URL"] as String
        val testSupabaseKey: String = properties["TEST_SUPABASE_KEY"] as String
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "TEST_SUPABASE_KEY",
            testSupabaseKey
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "TEST_SUPABASE_URL",
            testSupabaseUrl
        )
    }
}

kover {
    reports {
        filters {
            excludes {
                // Exclude generated code, DTOs, etc.
                classes("*.BuildConfig", "*.BuildKonfig")
                packages("com.ivangarzab.bookclub.data.remote.dtos")
                // Add other exclusions as needed
            }
        }
    }
}
