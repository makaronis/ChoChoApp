package com.makaroni.chocho

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChoChoApplication: Application() {
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}