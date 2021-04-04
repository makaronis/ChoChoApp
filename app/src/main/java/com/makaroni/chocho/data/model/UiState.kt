package com.makaroni.chocho.data.model

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
}
