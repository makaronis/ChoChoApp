package com.makaroni.chocho.features.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.theme.AppTheme
import com.makaroni.chocho.theme.Typography

@Composable
fun AuthScreen(viewModel: AuthViewModel) {

}

@Composable
fun AuthScreen() {
    Scaffold(modifier = Modifier) {

    }
}

@Composable
fun WelcomeBlock() {
//    Column(modifier = Modifier) {
//        Image(painter = painterResource(R.mipmap.ic_launcher), contentDescription = null)
        Text(modifier = Modifier, text = "Welcome!", style = Typography.h2)
//    }
}

@Composable
@Preview
fun WelcomeBlockPreview(){
    AppTheme {
        WelcomeBlock()
    }
}

