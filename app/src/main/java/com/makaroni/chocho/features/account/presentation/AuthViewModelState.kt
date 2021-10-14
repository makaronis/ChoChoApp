package com.makaroni.chocho.features.account.presentation

import com.makaroni.chocho.features.account.data.AuthScreen

data class AuthViewModelState(
    val currentScreen: AuthScreen,
    val isLoading: Boolean = false,
)