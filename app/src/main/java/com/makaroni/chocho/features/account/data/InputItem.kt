package com.makaroni.chocho.features.account.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.makaroni.chocho.common.data.ResId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class InputItem(
    val label: ResId,
    val state: MutableState<String> = mutableStateOf(""),
    var isInvalid: Boolean = true,
    val connectedItemId: Int? = null,
    val errorMessageState: MutableState<ResId?> = mutableStateOf(null),
    val onValueChange: (Int, String) -> Unit,
    private val validateField: (String) -> Boolean,
) {

    val text: String
        get() {
            return state.value.trim()
        }

    val errorMessage: ResId?
        get() {
            return errorMessageState.value
        }

    fun isFieldValid() = validateField(state.value)
}