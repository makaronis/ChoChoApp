package com.makaroni.chocho.features.common.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makaroni.chocho.R
import com.makaroni.chocho.theme.TrainsTheme
import com.makaroni.chocho.theme.Typography

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    intent: Intent,
    handleAuth: (ActivityResult) -> Unit
) {
    val openGoogleAuthActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = handleAuth
    )
    OutlinedButton(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        onClick = { openGoogleAuthActivity.launch(intent) },
        border = ButtonDefaults.outlinedBorder.copy(width = 3.dp),
        shape = CutCornerShape(15)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google_logo),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                text = "Continue with Google".uppercase(),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                style = Typography.button,
            )
        }
    }
}

@Composable
fun TrainsWideButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        shape = CutCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        onClick = onClick,
        enabled = isEnabled,
    ) {
        Text(text = text.uppercase(), textAlign = TextAlign.Center, style = Typography.button)
    }
}

@Composable
fun TrainsWideTextButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    TextButton(
        modifier = modifier.width(TrainsTheme.dimens.buttonWideWidth),
        shape = CutCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick
    ) {
        Text(
            text = text.uppercase(),
            textAlign = TextAlign.Center,
            style = Typography.button,
            color = MaterialTheme.colors.primary
        )
    }
}
