package com.makaroni.chocho.features.common.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.common.data.ResId
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography

@Composable
fun TrainsTextField(
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    label: String,
    text: String,
    errorMessage: ResId? = null,
    onValueChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
            value = text,
            onValueChange = onValueChange,
            shape = CutCornerShape(15),
            label = { Text(text = label) },
            maxLines = maxLines,
            keyboardActions = KeyboardActions(onDone = { this.defaultKeyboardAction(ImeAction.Next) }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            isError = errorMessage != null,
        )
        if (errorMessage != null) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(14.dp),
                text = stringResource(id = errorMessage.id),
                style = Typography.caption,
                color = MaterialTheme.colors.error
            )
        } else {
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}