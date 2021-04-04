package com.makaroni.chocho.data.model

sealed class UiEvent {
    data class Message(val msgId: Int?, val msg: String?) : UiEvent()
    data class Error(val msgId: Int?, val msg: String?, val exception: Exception?) : UiEvent()
}
