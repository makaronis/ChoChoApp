package com.makaroni.chocho.ext


import kotlin.Exception
import kotlin.coroutines.Continuation

fun <T> Continuation<T>.cancel(exception: Exception = Exception("Unknown error")) {
    this.resumeWith(Result.failure(exception))
}