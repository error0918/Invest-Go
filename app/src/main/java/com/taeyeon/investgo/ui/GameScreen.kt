package com.taeyeon.investgo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.taeyeon.investgo.model.GameViewModel
import com.taeyeon.investgo.model.MainViewModel

@Composable
fun GameScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current),
    name: String = "user"
) {
    LaunchedEffect(name) {
        mainViewModel.gameViewModel = GameViewModel()
    }

    Box(
        modifier = Modifier
    ) {}
}