package com.makaroni.chocho.features.account

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.features.common.presentation.GoogleButton
import com.makaroni.chocho.features.common.presentation.TrainsWideButton
import com.makaroni.chocho.features.common.presentation.TrainsWideTextButton
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography
import timber.log.Timber

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel,
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit,
    navigateToCollection: () -> Unit,
) {
    WelcomeScreen(
        navigateToLogin = navigateToLogin,
        handleAuth = viewModel::handleGoogleAuthResult,
        googleIntent = viewModel.googleSignInClient.signInIntent,
        navigateToSignup = navigateToSignup,
        navigateToCollection = navigateToCollection,
    )
}

@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    handleAuth: (ActivityResult) -> Unit,
    navigateToSignup: () -> Unit,
    navigateToCollection: () -> Unit,
    googleIntent: Intent
) {
    Scaffold(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            WelcomeBlock()
            Spacer(modifier = Modifier.height(70.dp))
            ButtonsBlock(
                navigateToLogin = navigateToLogin,
                navigateToSignup = navigateToSignup,
                intent = googleIntent,
                handleAuth = handleAuth
            )
        }
    }
}

@Composable
fun WelcomeBlock() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.ic_train),
            contentDescription = null
        )
        Text(modifier = Modifier, text = "Welcome!", style = Typography.h2)
    }
}

@Composable
fun ButtonsBlock(
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit,
    intent: Intent,
    handleAuth: (ActivityResult) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TrainsWideButton(text = "Sign up", onClick = navigateToSignup)
        GoogleButton(intent = intent, handleAuth = handleAuth)
        TrainsWideTextButton(text = "Log in", onClick = navigateToLogin)
    }
}



@Composable
@Preview
fun WelcomeBlockPreview() {
    TrainsTheme {
        WelcomeBlock()
    }
}

@Composable
@Preview
fun ButtonsBlockPreview() {
    TrainsTheme {
//        ButtonsBlock()
    }
}


@Composable
@Preview
fun AuthScreenPreview() {
    TrainsTheme {
        WelcomeScreen(
            navigateToLogin = {},
            navigateToSignup = {},
            navigateToCollection = {},
            googleIntent = Intent(),
            handleAuth = {}
        )
    }
}
