import org.gradle.internal.impldep.com.jcraft.jsch.ConfigRepository.defaultConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    `android-config`
}

android {
    defaultConfig {
        applicationId = "com.makaroni.chocho"
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
//    compileOptions {
//        sourceCompatibility(JavaVersion.VERSION_11)
//        targetCompatibility(JavaVersion.VERSION_11)
//    }
//
//
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_11.toString()
//    }
//    sourceSets {
//        // Adds exported schema location as test app assets.
//        androidTest.assets.srcDirs += files("$projectDir/schemas".toString())
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.1.1"
//    }

//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_11.toString()
//        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
//    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    // ===== android =====
    implementation(libs.bundles.android)
    implementation(libs.windowManager)
    // ===== compose =====
    implementation(libs.bundles.compose)
    debugImplementation(libs.composeTooling)
    implementation(libs.bundles.accompanist)

    // ===== dagger =====
    implementation(libs.dagger)
    kapt(libs.daggerCompiler)
    implementation(libs.hiltFragment)
    implementation(libs.hiltAndroid)
    implementation(libs.hiltNavigation)
    kapt(libs.hiltCompiler)

    // ===== serialization ====
    implementation(libs.bundles.serialization)

    // ===== room =====
    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)

    // ==== firebase ====
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)

    // ==== utils ====
    implementation(libs.viewBindingDelegate)
    implementation(libs.timber)

    // ===== tests =====
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidTest)

}