package com.makaroni.chocho.features.common.presentation

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makaroni.chocho.theme.TrainsTheme

@Composable
fun TrainsTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        value = text,
        onValueChange = onValueChange,
        shape = CutCornerShape(15),
        label = { Text(text = label) }
    )
}