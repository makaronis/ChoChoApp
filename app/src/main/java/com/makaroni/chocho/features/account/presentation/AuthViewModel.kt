package com.makaroni.chocho.features.account.presentation

import android.app.Application
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.makaroni.chocho.R
import com.makaroni.chocho.common.data.ResId
import com.makaroni.chocho.common.data.UiEvent
import com.makaroni.chocho.common.data.UiState
import com.makaroni.chocho.features.account.data.AuthScreen
import com.makaroni.chocho.features.account.data.AuthSideEffect
import com.makaroni.chocho.features.account.data.AuthViewModelState
import com.makaroni.chocho.features.account.data.UserInfo
import com.makaroni.chocho.features.account.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val appContext: Application
) : ViewModel() {

    private val eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val uiState = MutableStateFlow<UiState<Nothing>>(UiState.Idle)

    var password: String = "loki999"
    var passwordConfirm: String = "loki999"
    var email: String = "kokiys@yandex.ru"

    val nameState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordConfirmState = mutableStateOf("")
    val emailState = mutableStateOf("")

    val showHint = mutableStateOf(false)

    val googleSignInClient: GoogleSignInClient

    init {
        val options = getSignInOptions()
        googleSignInClient = GoogleSignIn.getClient(appContext, options)
    }

    private fun getSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("331283844427-7cirgci61kpfqjqt4h6eqtbnffbhutvi.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    fun handleGoogleAuthResult(result: ActivityResult) {
        Timber.v("handleGoogleAuthResult")
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val googleSignInAccount = task.getResult(ApiException::class.java)
            if (googleSignInAccount != null) {
                handleGoogleSuccess(googleSignInAccount)
            }
        } catch (e: ApiException) {
            handleGoogleFailure(e)
        }
    }

    private fun handleGoogleSuccess(account: GoogleSignInAccount) = viewModelScope.launch {
        Timber.v("handleGoogleSuccess")
        val googleTokenId = account.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        uiState.value = UiState.Loading
        try {
            val result = repo.signInGoogle(googleAuthCredential)
            handleAuthResult(result)
        } catch (e: Exception) {
            uiState.value = UiState.Idle
            eventChannel.trySend(UiEvent.Error())
            Timber.e(e, e.message.toString())
        }
    }


    fun signInWithGoogle(credentials: AuthCredential) {

    }

    private fun handleGoogleFailure(e: ApiException) {
        Timber.e(e, e.message)
    }

    fun signUpWithEmailPassword() {
        viewModelScope.launch {
            try {
                val result = repo.signInEmail(email, password)
                handleAuthResult(result)
            } catch (e: Exception) {
                uiState.value = UiState.Idle
                eventChannel.trySend(UiEvent.Error(exception = e))
                Log.e("TAG", e.message.toString(), e)
            }
        }
    }

    fun forgotPassword() {

    }

    private fun handleAuthResult(result: com.makaroni.chocho.features.account.data.AuthResult) {
        Timber.v("handleAuthResult")
        if (result.user == null) {
            uiState.value = UiState.Error()
            return
        }
        uiState.value = UiState.Idle
        Timber.v(result.toString())
        handleSideEffects(result.sideEffects, result.user)
    }

    private fun handleSideEffects(sideEffects: List<AuthSideEffect>, user: UserInfo) =
        sideEffects.forEach {
            when (it) {
                AuthSideEffect.VerifyEmail -> handleVerifyEmail()
                AuthSideEffect.PushToFirebase -> pushToDb(user)
                AuthSideEffect.Succeed -> navigateNext()
            }
        }

    private fun navigateNext() = viewModelScope.launch {
        //TODO navigate to collection fragment
//        eventChannel.send(UiEvent.NavigateTo(R.id.collection))
    }

    private fun pushToDb(userInfo: UserInfo) {
        viewModelScope.launch {
            try {
                repo.addUserToDb(userInfo)
                eventChannel.trySend(UiEvent.ShowSnackbar(ResId(R.string.db_update_success)))
            } catch (e: java.lang.Exception) {
                eventChannel.trySend(UiEvent.ShowSnackbar(ResId(R.string.db_update_failure)))
            }
        }
    }

    fun validatePassword(): Boolean = password.contains(Regex(passwordPattern))

    fun validateNewPassword() = validatePassword() && password == passwordConfirm

    fun validateEmail(): Boolean = email.contains(Regex(emailPattern))

    private fun handleVerifyEmail() {
        viewModelScope.launch {
            repo.sendEmailVerification()
            eventChannel.trySend(UiEvent.NavigateTo(ResId(R.id.authVerificationFragment)))
        }
    }

    fun onBackPressed() {

    }

    private val passwordPattern = "(?=.*[0-9a-zA-Z]).{6,}"
    private val emailPattern =
        "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

}