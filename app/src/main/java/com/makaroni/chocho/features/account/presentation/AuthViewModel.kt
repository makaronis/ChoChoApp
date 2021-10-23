package com.makaroni.chocho.features.account.presentation

import android.app.Application
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
import com.makaroni.chocho.features.account.data.*
import com.makaroni.chocho.features.account.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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

    val nameState = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordConfirmState = mutableStateOf("")
    val emailState = mutableStateOf("")

    val showHint = mutableStateOf(false)

    val googleSignInClient: GoogleSignInClient

    private val inputJobs = mutableMapOf<Int, Job?>()

    val inputItems =
        MutableStateFlow(
            mapOf(
                R.string.name to InputItem(
                    label = ResId(R.string.name),
                    onValueChange = ::handleInputItemUpdate,
                    validateField = ::validateName
                ),
                R.string.email to InputItem(
                    label = ResId(R.string.email),
                    onValueChange = ::handleInputItemUpdate,
                    validateField = ::validateEmail,
                ),
                R.string.password to InputItem(
                    label = ResId(R.string.password),
                    connectedItemId = R.string.confirm_password,
                    onValueChange = ::handleInputItemUpdate,
                    validateField = ::validatePassword,
                ),
                R.string.confirm_password to InputItem(
                    label = ResId(R.string.confirm_password),
                    onValueChange = ::handleInputItemUpdate,
                    validateField = ::validateNewPassword,
                )
            )
        )

    val isFieldsValid = MutableStateFlow(false)

    init {
        val options = getSignInOptions()
        googleSignInClient = GoogleSignIn.getClient(appContext, options)
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

    fun signInWithGoogle(credentials: AuthCredential) {

    }

    fun signUpWithEmailPassword() {
        viewModelScope.launch {
            try {
//                val result = repo.signInEmail(email, password)
//                handleAuthResult(result)
            } catch (e: Exception) {
                uiState.value = UiState.Idle
                eventChannel.trySend(UiEvent.Error(exception = e))
                Timber.e(e, e.message.toString())
            }
        }
    }

    fun forgotPassword() {

    }


    private fun handleAuthResult(result: AuthResult) {
        Timber.v("handleAuthResult")
        if (result.user == null) {
            uiState.value = UiState.Error()
            return
        }
        uiState.value = UiState.Idle
        Timber.v(result.toString())
        handleSideEffects(result.sideEffects, result.user)
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

    private fun handleGoogleFailure(e: ApiException) {
        Timber.e(e, e.message)
    }

    private fun handleVerifyEmail() {
        viewModelScope.launch {
            repo.sendEmailVerification()
            //TODO verify email change
//            eventChannel.trySend(UiEvent.NavigateTo(ResId(R.id.authVerificationFragment)))
        }
    }

    private fun getSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("331283844427-7cirgci61kpfqjqt4h6eqtbnffbhutvi.apps.googleusercontent.com")
            .requestEmail()
            .build()
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

    private fun navigateNext() = viewModelScope.launch {
        //TODO navigate to collection fragment
//        eventChannel.send(UiEvent.NavigateTo(R.id.collection))
    }


    private fun handleInputItemUpdate(key: Int, newText: String) {
        viewModelScope.launch {
            val item = inputItems.value[key] ?: return@launch
            item.state.value = newText
            item.handleSideEffects(scope = this)
        }
    }

    private suspend fun InputItem.handleSideEffects(
        delayed: Boolean = true,
        scope: CoroutineScope
    ) {
        inputJobs.cancelPreviousDelayedError(label.id)
        if (!isFieldValid()) {
            inputJobs.showErrorMessage(this@handleSideEffects, delayed = delayed, scope = scope)
        } else {
            isInvalid = false
            errorMessageState.value = null
            validateConnectedItem(inputItems.value, scope)
        }
        validateFields()
    }

    private suspend fun InputItem.validateConnectedItem(
        items: Map<Int, InputItem>,
        scope: CoroutineScope
    ) {
        val connectedItem = items[connectedItemId] ?: return
        connectedItem.handleSideEffects(delayed = false, scope = scope)
    }

    private fun MutableMap<Int, Job?>.cancelPreviousDelayedError(key: Int) {
        this[key]?.let {
            it.cancel()
            this[key] = null
        }
    }

    private suspend fun MutableMap<Int, Job?>.showErrorMessage(
        item: InputItem,
        scope: CoroutineScope,
        delayed: Boolean = true
    ) {
        item.isInvalid = true
        val key = item.label.id
        this@showErrorMessage[key] = scope.launch(Dispatchers.Default) {
            if (delayed) delay(2_000)
            Timber.d("delayEnded")
            item.errorMessageState.value = getMessageByKey(key)
            item.validateConnectedItem(inputItems.value, scope)
        }
    }

    private fun getMessageByKey(labelId: Int) = ResId(
        when (labelId) {
            R.string.name -> R.string.auth_name_error
            R.string.email -> R.string.auth_error_email
            R.string.password -> R.string.auth_error_password
            R.string.confirm_password -> R.string.auth_error_password_not_same
            else -> R.string.error_unknown
        }
    )

    private fun handleSideEffects(sideEffects: List<AuthSideEffect>, user: UserInfo) =
        sideEffects.forEach {
            when (it) {
                AuthSideEffect.VerifyEmail -> handleVerifyEmail()
                AuthSideEffect.PushToFirebase -> pushToDb(user)
                AuthSideEffect.Succeed -> navigateNext()
            }
        }


    private fun validateFields() { //todo refactor to combine flow operator
        val items = inputItems.value.values
        val correctFields = items.filter { !it.isInvalid && it.text.isNotEmpty() }
        isFieldsValid.value = correctFields.size == items.size
    }

    private fun validateName(name: String) = name.length > 1

    private fun validateEmail(email: String) = email.contains(Regex(emailPattern))

    private fun validatePassword(password: String): Boolean {
        return password.contains(Regex(passwordPattern))
    }

    private fun validateNewPassword(password: String): Boolean {
        return (validatePassword(password) && password == inputItems.value[R.string.password]?.text) || password.isEmpty()
    }


    private val passwordPattern = "(?=.*[0-9a-zA-Z]).{6,}"
    private val emailPattern =
        "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
}