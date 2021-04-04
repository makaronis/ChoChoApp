package com.makaroni.chocho.api

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.data.db.TrainModel
import com.makaroni.chocho.data.model.User

class FirestoreManager {

    private val firestore = Firebase.firestore

    fun addUser(user: User) {
        firestore.collection(USERS_COLLECTION).add(user)
            .addOnSuccessListener {
                it.id
                Log.d(MainActivityKt.TAG, "DocumentSnapshot added with path: ${it.path}")
                Log.d(MainActivityKt.TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.d(MainActivityKt.TAG,"error",it)
            }
    }

    fun addTrain(train:TrainModel) {
        firestore.collection(USERS_COLLECTION)
            .document("maAVtjuXb040gIt6aY76")
            .collection(TRAINS_COLLECTION)
            .add(train)
            .addOnSuccessListener {
                Log.d(MainActivityKt.TAG, "DocumentSnapshot added with path: ${it.path}")
                Log.d(MainActivityKt.TAG, "DocumentSnapshot added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.d(MainActivityKt.TAG,"error",it)
            }
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val TRAINS_COLLECTION = "trains"
    }
}