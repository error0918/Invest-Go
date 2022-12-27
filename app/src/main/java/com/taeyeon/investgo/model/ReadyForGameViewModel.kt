package com.taeyeon.investgo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taeyeon.investgo.data.Settings
import java.util.Calendar

class ReadyForGameViewModel : ViewModel() {
    val timeList = listOf(
        60 * 2 to "02:00",
        60 * 4 to "04:00",
        60 * 6 to "06:00"
    )
    var selected by mutableStateOf(Settings.DEFAULT_TIME_SELECTED)

    var gameStartCalendar: Calendar by mutableStateOf(Calendar.getInstance())
}