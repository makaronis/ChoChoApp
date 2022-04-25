// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url= "https://maven.google.com")
}
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:+")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        // Crashlytics
        classpath("com.google.gms:google-services:4.3.10") // Google Services plugin
        // Add dependency
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}


allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}