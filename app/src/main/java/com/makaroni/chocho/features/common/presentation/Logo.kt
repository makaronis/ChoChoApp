package com.makaroni.chocho.features.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makaroni.chocho.R
import com.makaroni.chocho.theme.Montserrat
import com.makaroni.chocho.theme.TrainsTheme

@Composable
fun TrainsLogo() {
    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(75.dp),
            painter = painterResource(R.drawable.ic_train),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "MyTrains!",
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            fontFamily = Montserrat
        )
    }
}

@Composable
@Preview
fun TrainsLogoPreview() {
    TrainsTheme {
        androidx.compose.material.Surface {
            TrainsLogo()
        }
    }
}