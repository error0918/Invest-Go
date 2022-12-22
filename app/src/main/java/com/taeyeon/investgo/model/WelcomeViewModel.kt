package com.taeyeon.investgo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {
    var userName by mutableStateOf("")
    var userNameErrorMessage by mutableStateOf<String?>(null)
}