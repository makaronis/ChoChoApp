package com.makaroni.chocho.features.account.domain

import com.google.firebase.auth.AuthCredential
import com.makaroni.chocho.features.account.data.AuthResult
import com.makaroni.chocho.features.account.data.UserInfo

interface AuthRepository {
    suspend fun signInGoogle(credentials: AuthCredential): AuthResult
    suspend fun signInEmail(email: String, password: String): AuthResult
    suspend fun sendEmailVerification()
    suspend fun addUserToDb(user: UserInfo): Boolean
    fun getCurrentUser(): UserInfo?
}