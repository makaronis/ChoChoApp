package com.makaroni.chocho

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.makaroni.chocho.TrainsDestinations.LOGIN_SCREEN

object TrainsDestinations {
    const val AUTH_ROUTE = "auth"
    const val WELCOME_SCREEN = "welcome"
    const val LOGIN_SCREEN = "login"
    const val SIGN_UP_SCREEN = "signUp"
}

class TrainsAppRouter(navController: NavController) {
    val navigateBack: () -> Unit = {
        navController.popBackStack()
        Unit
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(LOGIN_SCREEN)
    }

    val navigateToSignUp: () -> Unit = {
        navController.navigate(TrainsDestinations.SIGN_UP_SCREEN)
    }

    val navigateToWelcome: () -> Unit = {
        navController.navigate(TrainsDestinations.AUTH_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

}