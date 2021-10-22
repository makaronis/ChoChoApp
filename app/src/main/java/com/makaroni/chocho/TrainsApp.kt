package com.makaroni.chocho

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.*
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.utils.WindowSize
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalAnimatedInsets
@ExperimentalAnimationApi
@Composable
fun TrainsApp(windowSize: WindowSize) {
    TrainsTheme {
        ProvideWindowInsets() {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
            }

            val navController = rememberAnimatedNavController()
            val navRouter = TrainsAppRouter(navController)
            val coroutineScope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute =
                navBackStackEntry?.destination?.route ?: TrainsDestinations.AUTH_ROUTE

            val isExpandedScreen = windowSize == WindowSize.Expanded
            val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

            ModalDrawer(
                drawerContent = { AppDrawer() },
                drawerState = sizeAwareDrawerState,
                // Only enable opening the drawer via gestures if the screen is not expanded
                gesturesEnabled = !isExpandedScreen
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(bottom = false)
                        .imePadding()
                ) {
                    if (isExpandedScreen) {
                        //TODO navRail for landscape
                    }
                    Timber.v("TrainsApp")
                    TrainsNavGraph(
                        isExpandedScreen = isExpandedScreen,
                        navController = navController,
                        navRouter = navRouter,
                        openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } }
                    )
                }
            }
        }
    }
}

/**
 * Determine the drawer state to pass to the modal drawer.
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}