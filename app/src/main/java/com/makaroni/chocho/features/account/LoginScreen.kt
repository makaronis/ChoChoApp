package com.makaroni.chocho.features.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.features.common.presentation.BackArrowToolbar
import com.makaroni.chocho.features.common.presentation.TrainsLogo
import com.makaroni.chocho.features.common.presentation.TrainsTextField
import com.makaroni.chocho.features.common.presentation.TrainsWideButton
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navigateBack: () -> Unit) {
    LoginScreen(navigateBack = navigateBack)
}

@Composable
fun LoginScreen(navigateBack: () -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Scaffold(topBar = { BackArrowToolbar(navigateBack = navigateBack) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(modifier = Modifier.padding(top = 8.dp), text = "Log in", style = Typography.h2)
            TrainsTextField(
                modifier = Modifier.padding(top = 8.dp),
                label = "Email",
                text = email.component1(),
                onValueChange = email.component2(),
            )
            TrainsTextField(
                modifier = Modifier.padding(top = 8.dp),
                label = "Password",
                text = password.component1(),
                onValueChange = password.component2(),
            )
            TrainsWideButton(
                modifier = Modifier.padding(top = 16.dp),
                text = "Sign In"
            )
        }
    }
}


@Composable
@Preview
fun SignInScreenPreview() {
    TrainsTheme {
        LoginScreen(navigateBack = {})
    }
}
