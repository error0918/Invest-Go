package com.taeyeon.investgo.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator

class MainViewModel(context: Context) : ViewModel() {
    val navHostController = NavHostController(context).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }

    val welcomeViewModel by mutableStateOf(WelcomeViewModel())
    var gameViewModel by mutableStateOf(GameViewModel())
}