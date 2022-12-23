package com.taeyeon.investgo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taeyeon.investgo.data.Settings
import com.taeyeon.investgo.ui.getRandomName
import com.taeyeon.investgo.util.getDigitNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(
    name: String = getRandomName(),
    time: Int = 60 * 5
) : ViewModel() {
    private var isTimerWorking = true
    private var remainingSeconds = time

    val name by mutableStateOf(name)
    var remainingVisibleTime by mutableStateOf("${getDigitNumber(remainingSeconds / 60, 2)}:${getDigitNumber(remainingSeconds % 60, 2)}")

    var score by mutableStateOf(Settings.DEFAULT_MONEY)
    var won by mutableStateOf(Settings.DEFAULT_MONEY)

    var isMenuPopupShowing by mutableStateOf(false)


    init {
        CoroutineScope(Dispatchers.IO).launch {
            while (isTimerWorking) {
                delay(1000)
                remainingSeconds--
                remainingVisibleTime = "${getDigitNumber(remainingSeconds / 60, 2)}:${getDigitNumber(remainingSeconds % 60, 2)}"
                if (remainingSeconds <= 0) stopTimer()
            }
        }
    }


    fun stopTimer() {
        isTimerWorking = false
    }

    fun resumeTimer() {
        isTimerWorking = true
    }
}