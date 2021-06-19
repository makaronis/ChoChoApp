package com.makaroni.chocho.features.account.data

data class AuthResult(val user: UserInfo?, val sideEffects: List<AuthSideEffect>)

enum class AuthSideEffect {
    PushToFirebase, VerifyEmail, Succeed
}