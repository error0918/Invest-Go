package com.taeyeon.investgo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReadyForGameViewModel : ViewModel() {
    val timeList = listOf(
        60 * 2 to "02:00",
        60 * 5 to "05:00",
        60 * 7 to "07:00"
    )
    var selected by mutableStateOf(1)
}