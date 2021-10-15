package com.makaroni.chocho.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.makaroni.chocho.features.common.presentation.TrainsLogo

@Composable
fun HomeScreen() {
    Scaffold {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TrainsLogo()
        }
    }
}