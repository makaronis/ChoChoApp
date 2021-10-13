package com.makaroni.chocho

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.makaroni.chocho.TrainsDestinations.LOGIN_ROUTE

object TrainsDestinations {
    const val WELCOME_ROUTE = "welcome"
    const val LOGIN_ROUTE = "login"
    const val SIGN_UP_ROUTE = "signUp"
}

class TrainsAppNavigationActions(navController: NavController) {
    val navigateToLogin: () -> Unit = {
        navController.navigate(LOGIN_ROUTE)
    }

    val navigateToSignUp: () -> Unit = {
        navController.navigate(TrainsDestinations.SIGN_UP_ROUTE)
    }

    val navigateToWelcome: () -> Unit = {
        navController.navigate(TrainsDestinations.WELCOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

}