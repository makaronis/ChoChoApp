plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.6.10")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
    // Crashlytics
    implementation("com.google.gms:google-services:4.3.10") // Google Services plugin
    // Add dependency
    implementation("com.google.firebase:firebase-crashlytics-gradle:2.8.1")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
}