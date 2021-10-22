package com.makaroni.chocho.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.makaroni.chocho.R
import com.makaroni.chocho.features.common.presentation.TrainsLogo
import com.makaroni.chocho.theme.TrainsTheme

@Composable
fun HomeScreen() {
    Scaffold {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TrainsLogo()
        }
    }
}

@Composable
fun TrainModelItem(
    type: String,
    subtype: String,
    model: String,
    company: String,
    article: String,
    manufacturer: String
) {
    Card(backgroundColor = MaterialTheme.colors.primaryVariant) {
        Row {
            Image(
                modifier = Modifier.weight(1.3f),
                painter = painterResource(id = R.drawable.imagetest),
                contentDescription = null
            )
            InformationBlock(
                modifier = Modifier.weight(1f),
                type = type,
                subtype = subtype,
                model = model,
                company = company,
                article = article,
                manufacturer = manufacturer
            )
        }
    }
}

@Composable
fun InformationBlock(
    modifier: Modifier = Modifier,
    type: String,
    subtype: String,
    model: String,
    company: String,
    article: String,
    manufacturer: String
) {
    Column(modifier = modifier) {
        Row {
            Text(text = type)
            Text(text = subtype)
        }
        Row {
            Text(text = model)
            Text(text = company)
        }
        Row {
            Text(text = "Art. No:")
            Text(text = article)
        }
        Text(text = manufacturer)
    }
}

@Composable
@Preview
fun TrainModelItemPreview() {
    TrainsTheme {
        TrainModelItem(
            type = "Steam",
            subtype = "Locomotive",
            model = "class 45450",
            company = "OBB",
            article = "734618",
            manufacturer = "Roco"
        )
    }
}