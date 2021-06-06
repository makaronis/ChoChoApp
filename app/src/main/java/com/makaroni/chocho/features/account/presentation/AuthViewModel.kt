package com.makaroni.chocho.features.account.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.makaroni.chocho.R
import com.makaroni.chocho.features.account.data.AuthSideEffect
import com.makaroni.chocho.features.account.data.UserInfo
import com.makaroni.chocho.features.account.domain.AuthRepository
import com.makaroni.chocho.common.data.ResId
import com.makaroni.chocho.common.data.UiEvent
import com.makaroni.chocho.common.data.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {

    private val eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    val uiState = MutableStateFlow<UiState<Nothing>>(UiState.Idle)

    init {
        viewModelScope.launch {
//            delay(2_000)
//            uiState.value = UiState.
        }
    }

    fun signInWithGoogle(credentials: AuthCredential) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            try {
                val result = repo.signInGoogle(credentials)
                if (result.user == null) {
                    uiState.value = UiState.Error()
                    return@launch
                }
                uiState.value = UiState.Idle
                Log.d("TAG", result.toString())

                result.sideEffects.forEach {
                    when (it) {
                        AuthSideEffect.VerifyEmail -> handleVerifyEmail()
                        AuthSideEffect.PushToFirebase -> pushToDb(result.user)
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error()
            }
        }
    }

    private fun pushToDb(userInfo: UserInfo) {
        viewModelScope.launch {
            try {
                repo.addUserToDb(userInfo)
                eventChannel.offer(UiEvent.ShowSnackbar(ResId(R.string.db_update_success)))
            } catch (e: java.lang.Exception) {
                eventChannel.offer(UiEvent.ShowSnackbar(ResId(R.string.db_update_failure)))
            }
        }
    }

    private fun handleVerifyEmail() =
        eventChannel.offer(UiEvent.NavigateTo(ResId(R.id.authVerificationFragment)))
}