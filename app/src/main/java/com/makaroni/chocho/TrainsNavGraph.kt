package com.makaroni.chocho

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.makaroni.chocho.features.account.LoginScreen
import com.makaroni.chocho.features.account.SignUpScreen
import com.makaroni.chocho.features.account.WelcomeScreen
import com.makaroni.chocho.features.account.presentation.AuthViewModel


@ExperimentalAnimationApi
@Composable
fun TrainsNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    navRouter: TrainsAppRouter,
    openDrawer: () -> Unit = {},
    startDestination: String = TrainsDestinations.AUTH_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authGraph(navRouter)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.authGraph(navRouter: TrainsAppRouter) {
    navigation(
        startDestination = TrainsDestinations.WELCOME_SCREEN,
        route = TrainsDestinations.AUTH_ROUTE
    ) {
        composable(TrainsDestinations.WELCOME_SCREEN) {
            val viewModel = hiltViewModel<AuthViewModel>()
            WelcomeScreen(
                viewModel = viewModel,
                navigateToLogin = navRouter.navigateToLogin,
                navigateToSignup = navRouter.navigateToSignUp
            )
        }
        composable(TrainsDestinations.LOGIN_SCREEN) {
            val viewModel = hiltViewModel<AuthViewModel>()
            LoginScreen(authViewModel = viewModel, navigateBack = navRouter.navigateBack)
        }
        composable(TrainsDestinations.SIGN_UP_SCREEN) {
            val viewModel = hiltViewModel<AuthViewModel>()
            SignUpScreen(viewModel = viewModel, navigateBack = navRouter.navigateBack)
        }
    }
}