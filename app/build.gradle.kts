import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.futureus.cameraxapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.futureus.cameraxapp"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val supabaseAnoKey: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_ANON_KEY") ?: ""
        val supabaseUrl: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_URL") ?: ""
        val supabaseRole: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_ROLE") ?: ""
        val secret: String = gradleLocalProperties(rootDir, providers).getProperty("SECRET") ?: ""
        buildConfigField("String", "API_KEY", "\"$supabaseAnoKey\"")
        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ROLE", "\"$supabaseRole\"")
        buildConfigField("String", "SECRET", "\"$secret\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.dagger.hilt.androidx)
    implementation(libs.dagger.hilt.navigation.compose)

    // Serialization
    implementation(libs.bundles.serialization)


    // Superbase
    implementation(libs.bundles.supabase)

    // Ktor
    implementation(libs.bundles.ktor)

    // Coil
    implementation(libs.coil.kt.coil.compose)

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.flowlayout)

    // RamCosta Navigation
    implementation(libs.compose.destination.core)
    ksp(libs.compose.destination.ksp)

    // Navigation animation
    implementation(libs.accompanist.navigation.animation)

    // Coil Video
    implementation(libs.coil.video)

    // Exo Player
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.hls)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.firebase.messaging.ktx)

    implementation (libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation("com.google.accompanist:accompanist-permissions:0.33.1-alpha")

}