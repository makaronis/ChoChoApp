package com.makaroni.chocho

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChoChoApplication: Application() {
    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        Timber.d("onApplicationCreate")
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}