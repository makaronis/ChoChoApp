package com.makaroni.chocho.features.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography

@Composable
fun SignInScreen(authViewModel: AuthViewModel) {

}

@Composable
fun SignInScreen() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Scaffold(topBar = { BackArrowToolbar() }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = R.drawable.ic_train),
                contentDescription = null
            )
            Text(modifier = Modifier.padding(top = 8.dp), text = "Sign In", style = Typography.h2)
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
fun TrainsTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        value = text,
        onValueChange = onValueChange,
        shape = CutCornerShape(15),
        label = { Text(text = label) }
    )
}

@Composable
fun BackArrowToolbar() {
    TopAppBar(navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            
        }
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null
        )
    }, title = {})
}

@Composable
@Preview
fun SignInScreenPreview() {
    TrainsTheme {
        SignInScreen()
    }
}
