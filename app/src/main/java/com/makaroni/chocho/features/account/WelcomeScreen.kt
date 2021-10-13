package com.makaroni.chocho.features.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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

@Composable
fun WelcomeScreen(viewModel: AuthViewModel) {
    WelcomeScreen()
}

@Composable
fun WelcomeScreen() {
    Scaffold(modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            WelcomeBlock()
            Spacer(modifier = Modifier.height(70.dp))
            ButtonsBlock()
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
fun ButtonsBlock() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TrainsWideButton(text = "Sign up")
        GoogleButton {

        }
        TrainsWideTextButton(text = "Log in")
    }
}

@Composable
fun GoogleButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        onClick = onClick,
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
        ButtonsBlock()
    }
}


@Composable
@Preview
fun AuthScreenPreview() {
    TrainsTheme {
        WelcomeScreen()
    }
}
