package com.makaroni.chocho.features.account.domain

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.features.account.data.AuthResult
import com.makaroni.chocho.features.account.data.AuthSideEffect
import com.makaroni.chocho.features.account.data.UserInfo
import com.makaroni.chocho.api.FirestoreManager
import com.makaroni.chocho.exceptions.FirestoreUpdateError
import com.makaroni.chocho.ext.cancel
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthRepositoryImpl : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    override suspend fun signInGoogle(credentials: AuthCredential): AuthResult =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithCredential(credentials).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val isNewUser = task.result?.additionalUserInfo?.isNewUser == true
                    val firebaseUser = firebaseAuth.currentUser ?: kotlin.run {
                        continuation.cancel()
                        return@addOnCompleteListener
                    }
                    //TODO check if email always returns
                    val accountInfo = getUserInfo(firebaseUser)
                    val sideEffect =
                        if (isNewUser) listOf(AuthSideEffect.PushToFirebase)
                        else emptyList()

                    continuation.resumeWith(Result.success(AuthResult(accountInfo, sideEffect)))
                } else {
                    continuation.cancel(task.exception ?: Exception())
                }
            }
        }

    override suspend fun signInEmail(email: String, password: String): AuthResult =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val isNewUser = task.result?.additionalUserInfo?.isNewUser == true
                        val firebaseUser = firebaseAuth.currentUser ?: kotlin.run {
                            continuation.cancel()
                            return@addOnCompleteListener
                        }
                        val accountInfo = getUserInfo(firebaseUser)
                        val sideEffects = mutableListOf<AuthSideEffect>()

                        if (isNewUser) sideEffects.add(AuthSideEffect.PushToFirebase)
                        if (!firebaseUser.isEmailVerified) sideEffects.add(AuthSideEffect.VerifyEmail)

                        continuation.resumeWith(
                            Result.success(
                                AuthResult(
                                    accountInfo,
                                    sideEffects
                                )
                            )
                        )
                    } else {
                        continuation.cancel(task.exception ?: Exception())
                    }
                }
        }

    override suspend fun sendEmailVerification() {
        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {

            } else {

            }
        }
    }

    private fun getUserInfo(user: FirebaseUser?): UserInfo? = if (user == null) null else UserInfo(
        name = user.displayName,
        email = user.email ?: "Unknown email",
        uid = user.uid,
    )

    override suspend fun addUserToDb(user: UserInfo): Boolean =
        suspendCoroutine { continuation ->
            firestore.collection(FirestoreManager.USERS_COLLECTION).document(user.uid).set(user)
                .addOnSuccessListener {
//                    it.id
//                    Log.d(MainActivityKt.TAG, "DocumentSnapshot added with path: ${it.path}")
//                    Log.d(MainActivityKt.TAG, "DocumentSnapshot added with ID: ${it.id}")
                    continuation.resumeWith(Result.success(true))
                }
                .addOnFailureListener {
                    Log.d(MainActivityKt.TAG, "error", it)
                    continuation.resumeWith(Result.failure(FirestoreUpdateError()))
                }
        }

    override fun getCurrentUser(): UserInfo? {
        return getUserInfo(firebaseAuth.currentUser)
    }
}