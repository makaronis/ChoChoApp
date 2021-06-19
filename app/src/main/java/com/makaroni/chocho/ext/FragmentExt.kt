package com.makaroni.chocho.ext

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.makaroni.chocho.MainActivityKt
import com.makaroni.chocho.R
import com.makaroni.chocho.common.data.ResId
import com.makaroni.chocho.common.data.UiEvent
import java.time.Duration

fun Fragment.showSnackbar(view: View, data: UiEvent.ShowSnackbar) {
    Snackbar.make(view, data.msg.id, data.duration).show()
}

fun Fragment.showErrorSnackbar(view: View, data: UiEvent.Error) {
    val text = data.exception?.message ?: getString(data.msg.id)
    val bar = Snackbar.make(view, text, data.duration)
    bar.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorError))
    bar.show()
}

fun Fragment.showProgressBar() {
    val activity = requireActivity() as MainActivityKt
    activity.showProgressBar()
}

fun Fragment.hideProgressBar() {
    val activity = requireActivity() as MainActivityKt
    activity.hideProgressBar()
}

