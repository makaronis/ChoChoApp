package com.makaroni.chocho

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makaroni.chocho.features.account.WelcomeScreen
import com.makaroni.chocho.features.account.presentation.AuthViewModel

@Composable
fun TrainsNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = TrainsDestinations.WELCOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(TrainsDestinations.WELCOME_ROUTE) {
            val viewModel = hiltViewModel<AuthViewModel>()
            WelcomeScreen(viewModel = viewModel)
        }
    }
}