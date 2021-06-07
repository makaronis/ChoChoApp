package com.makaroni.chocho

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChoChoApplication: Application() {
    override fun onCreate() {
        Log.d("TAG","onApplicationCreate")
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}