plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.watchtogether"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.watchtogether"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Load debug environment variables from local.properties
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                val properties = localPropertiesFile.readLines()
                    .filter { it.isNotBlank() && !it.startsWith("#") }
                    .associate { 
                        val parts = it.split("=", limit = 2)
                        if (parts.size == 2) parts[0] to parts[1].trim() else "" to ""
                    }
                
                // Helper function to properly escape strings for buildConfigField
                fun escapeString(str: String): String {
                    return str.replace("\\", "\\\\")
                            .replace("\"", "\\\"")
                }
                
                val supabaseUrl = properties["SUPABASE_URL"] ?: ""
                val supabaseKey = properties["SUPABASE_KEY"] ?: ""
                
                buildConfigField("String", "SUPABASE_URL", "\"${escapeString(supabaseUrl)}\"")
                buildConfigField("String", "SUPABASE_KEY", "\"${escapeString(supabaseKey)}\"")
            }
        }
        
        release {
            // Use environment variables from CI/CD if available, otherwise empty
            val supabaseUrl = System.getenv("SUPABASE_URL") ?: ""
            val supabaseKey = System.getenv("SUPABASE_KEY") ?: ""
            
            // Helper function to properly escape strings for buildConfigField
            fun escapeString(str: String): String {
                return str.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
            }
            
            buildConfigField("String", "SUPABASE_URL", "\"${escapeString(supabaseUrl)}\"")
            buildConfigField("String", "SUPABASE_KEY", "\"${escapeString(supabaseKey)}\"")
            
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.constraintlayout)

    // In your app's build.gradle file
    // Add OkHttp integration for Coil
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.ktor:ktor-client-android:3.1.1")
    implementation("io.ktor:ktor-client-content-negotiation:3.1.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1")

    // Add Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    // Koin Dependency Injection
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")
    
    // DotEnv for environment variables
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
}