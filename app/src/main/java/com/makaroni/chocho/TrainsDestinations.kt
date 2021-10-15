package com.makaroni.chocho

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object TrainsDestinations {
    const val AUTH_ROUTE = "auth"
    const val WELCOME_SCREEN = "welcome"
    const val LOGIN_SCREEN = "login"
    const val SIGN_UP_SCREEN = "signUp"

    const val COLLECTION_ROUTE = "collection"
    const val HOME_SCREEN = "home"
}


class TrainsAppRouter(navController: NavController) {
    val navigateBack: () -> Unit = {
        navController.popBackStack()
        Unit
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(TrainsDestinations.LOGIN_SCREEN)
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

    val navigateToCollection: () -> Unit= {
        navController.navigate(TrainsDestinations.COLLECTION_ROUTE){
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }


}