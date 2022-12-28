package com.taeyeon.investgo.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class GameData(
    val marketData: List<TradeCowData> = getCopiedMarketData(),
    val propertyData: SnapshotStateList<SnapshotStateList<Pair<Int, Float>>> = marketData.let {
        val outerSnapshotStateList = mutableStateListOf<SnapshotStateList<Pair<Int, Float>>>()
        it.forEach { tradeCowData ->
            val innerSnapshotStateList = mutableStateListOf<Pair<Int, Float>>()
            for (index in tradeCowData.stockDataList.indices) innerSnapshotStateList.add(0 to 0f)
            outerSnapshotStateList.add(innerSnapshotStateList)
        }
        outerSnapshotStateList
    }, // 개수 to 평균 단가
    var won: Float = Settings.DEFAULT_MONEY
) {
    fun getScore(): Float {
        var score = won
        for (outerIndex in propertyData.indices) {
            for (innerIndex in propertyData[outerIndex].indices) {
                score += marketData[outerIndex].stockDataList[innerIndex].stockPriceData.price * propertyData[outerIndex][innerIndex].first
            }
        }
        return score
    }
}
