package com.makaroni.chocho.common.data

import com.google.android.material.snackbar.Snackbar
import com.makaroni.chocho.R

sealed interface UiEvent {
    data class ShowSnackbar(val msg: ResId, val duration: Int = Snackbar.LENGTH_SHORT) : UiEvent
    data class Error(
        val msg: ResId = ResId(R.string.error_unknown),
        val exception: Exception? = Exception(),
        val duration: Int = Snackbar.LENGTH_SHORT
    ) : UiEvent

    data class NavigateTo(val route: String) : UiEvent
}
