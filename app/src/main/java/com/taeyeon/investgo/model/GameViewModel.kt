package com.taeyeon.investgo.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.taeyeon.investgo.data.GameData
import com.taeyeon.investgo.ui.getRandomName
import com.taeyeon.investgo.util.getDigitNumber
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


sealed class GameSubScreen(val fraction: Float = 0.5f) {
    object Default : GameSubScreen()
    class Chart(
        val tradeCowIndex: Int,
        val stockDataIndex: Int
    ) : GameSubScreen(fraction = 0.7f)
}


class GameViewModel(
    val name: String = getRandomName(),
    val time: Int = 60 * 5
) : ViewModel() {
    private var isTimerWorking = true
    private var isTimerDoing = true
    private var remainingSeconds = time

    var remainingVisibleTime by mutableStateOf("${getDigitNumber(remainingSeconds / 60, 2)}:${getDigitNumber(remainingSeconds % 60, 2)}")

    var gameData by mutableStateOf(GameData().copy())

    var isShowingMenu by mutableStateOf(false)
    var isStoped by mutableStateOf(false)
    var isShowingEnding by mutableStateOf(false)
    var subScreen by mutableStateOf<GameSubScreen>(GameSubScreen.Default)


    init {
        CoroutineScope(Dispatchers.IO).launch {
            var count = 0
            while (isTimerWorking) {
                if (isTimerDoing) {
                    delay(100)
                    count++

                    gameData.marketData.forEach { tradeCowData ->
                        tradeCowData.stockDataList.forEach { stockData ->
                            stockData.update()
                        }
                    }

                    if (count % 10 == 0) {
                        remainingSeconds--
                        remainingVisibleTime = "${getDigitNumber(remainingSeconds / 60, 2)}:${getDigitNumber(remainingSeconds % 60, 2)}"
                        if (remainingSeconds <= 0) {
                            endTimer()
                        }
                    }
                }
            }
        }
    }


    fun resumeTimer() {
        isTimerDoing = true
    }

    fun stopTimer() {
        isTimerDoing = false
    }

    fun endTimer() {
        isTimerWorking = false
        isStoped = true
    }
}