package com.makaroni.chocho.features.account

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.rememberImeNestedScrollConnection
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.presentation.AuthViewModel
import com.makaroni.chocho.features.common.presentation.BackArrowToolbar
import com.makaroni.chocho.features.common.presentation.TrainsLogo
import com.makaroni.chocho.features.common.presentation.TrainsTextField
import com.makaroni.chocho.features.common.presentation.TrainsWideButton
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography
import timber.log.Timber

@ExperimentalAnimatedInsets
@Composable
fun SignUpScreen(viewModel: AuthViewModel, navigateBack: () -> Unit) {
    SignUpScreen(
        nameState = viewModel.nameState,
        emailState = viewModel.emailState,
        passwordState = viewModel.passwordState,
        passwordConfirmState = viewModel.passwordConfirmState,
        showHint = viewModel.showHint.component1(),
        navigateBack = navigateBack,
        onContinueClick = viewModel::signUpWithEmailPassword,
    )
}

@ExperimentalAnimatedInsets
@Composable
fun SignUpScreen(
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    passwordConfirmState: MutableState<String>,
    showHint: Boolean,
    navigateBack: () -> Unit,
    onContinueClick: () -> Unit,
) {
    Scaffold(topBar = { BackArrowToolbar(navigateBack) }) { contentPadding->
        Timber.d(contentPadding.toString())
        Crossfade(targetState = showHint) { showHint ->
            if (showHint) {
                HintContent()
            } else {
                InputContent(
                    contentPadding = contentPadding,
                    nameState = nameState,
                    emailState = emailState,
                    passwordState = passwordState,
                    passwordConfirmState = passwordConfirmState,
                    onContinueClick = onContinueClick,
                )
            }
        }
    }
}

@ExperimentalAnimatedInsets
@Composable
fun InputContent(
    contentPadding:PaddingValues,
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    passwordConfirmState: MutableState<String>,
    onContinueClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.auth_create_account),
            style = Typography.h3,
            textAlign = TextAlign.Center
        )
        InputBlock(
            nameState = nameState,
            emailState = emailState,
            passwordState = passwordState,
            passwordConfirmState = passwordConfirmState,
        )
        TrainsWideButton(
            modifier = Modifier.padding(top = 16.dp),
            text = "Continue",
            isEnabled = false,
            onClick = onContinueClick,
        )
    }
}

@Composable
fun InputBlock(
    nameState: MutableState<String>,
    emailState: MutableState<String>,
    passwordState: MutableState<String>,
    passwordConfirmState: MutableState<String>,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TrainsTextField(
            label = stringResource(id = R.string.name),
            text = nameState.component1(),
            onValueChange = nameState.component2(),
            maxLines = 1,
        )
        TrainsTextField(
            label = stringResource(id = R.string.email),
            text = emailState.component1(),
            onValueChange = emailState.component2(),
            maxLines = 1,
        )
        TrainsTextField(
            label = stringResource(id = R.string.password),
            text = passwordState.component1(),
            onValueChange = passwordState.component2(),
            maxLines = 1,
        )
        TrainsTextField(
            label = stringResource(id = R.string.confirm_password),
            text = passwordConfirmState.component1(),
            onValueChange = passwordConfirmState.component2(),
            maxLines = 1,
        )
    }
}

@Composable
fun HintContent() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TrainsLogo()
        Text(
            text = stringResource(id = R.string.auth_verification_title),
            style = Typography.h2,
            textAlign = TextAlign.Center,
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(id = R.string.auth_verification_msg),
                style = Typography.h4,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@ExperimentalAnimatedInsets
@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun SignUpScreenPreview() {
    TrainsTheme {
        SignUpScreen(
            nameState = mutableStateOf(""),
            emailState = mutableStateOf(""),
            passwordState = mutableStateOf(""),
            passwordConfirmState = mutableStateOf(""),
            showHint = false,
            navigateBack = { },
            onContinueClick = {},
        )
    }
}

@Composable
@Preview
fun HintContentPreview() {
    TrainsTheme {
        HintContent()
    }
}