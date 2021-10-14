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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography
import timber.log.Timber

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel,
    navigateToLogin: () -> Unit,
    navigateToSignup: () -> Unit
) {
    WelcomeScreen(
        navigateToLogin = navigateToLogin,
        handleAuth = viewModel::handleGoogleAuthResult,
        googleIntent = viewModel.googleSignInClient.signInIntent,
        navigateToSignup = navigateToSignup,
    )
}

@Composable
fun WelcomeScreen(
    navigateToLogin: () -> Unit,
    handleAuth: (ActivityResult) -> Unit,
    navigateToSignup: () -> Unit,
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
fun GoogleButton(
    modifier: Modifier = Modifier,
    intent: Intent,
    handleAuth: (ActivityResult) -> Unit
) {
    val openGoogleAuthActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = handleAuth
    )
    OutlinedButton(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        onClick = { openGoogleAuthActivity.launch(intent) },
        border = ButtonDefaults.outlinedBorder.copy(width = 3.dp),
        shape = CutCornerShape(15)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                text = "Continue with Google".uppercase(),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                style = Typography.button,
            )
        }
    }
}

@Composable
fun TrainsWideButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        shape = CutCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        onClick = onClick
    ) {
        Text(text = text.uppercase(), textAlign = TextAlign.Center, style = Typography.button)
    }
}

@Composable
fun TrainsWideTextButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    TextButton(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        shape = CutCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = Typography.button,
            color = MaterialTheme.colors.primary
        )
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
            googleIntent = Intent(),
            handleAuth = {}
        )
    }
}
