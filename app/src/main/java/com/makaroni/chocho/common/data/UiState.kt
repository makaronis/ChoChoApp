package com.makaroni.chocho.common.data

import com.makaroni.chocho.R
import java.lang.Exception

sealed interface UiState<out T : Any> {
    object Idle : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Error(
        val msgId: ResId = ResId(R.string.error_unknown),
        val exception: Exception? = null
    ) : UiState<Nothing>

    data class Success<out T : Any>(val data: T) : UiState<T>

}
