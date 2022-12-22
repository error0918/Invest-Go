package com.taeyeon.investgo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.util.spinningGradientBackground

@Composable
fun ReadyForGameScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .spinningGradientBackground(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary
                )
            )
            .padding(32.dp)
    )
}