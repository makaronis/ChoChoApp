package com.makaroni.chocho

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.makaroni.chocho.di.MainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class ChoChoApplication: Application() {
    override fun onCreate() {
        Log.d("TAG","onApplicationCreate")
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@ChoChoApplication)
            modules(MainModule.module)
        }
        super.onCreate()
    }
}