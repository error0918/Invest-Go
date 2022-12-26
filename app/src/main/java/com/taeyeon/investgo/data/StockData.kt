package com.taeyeon.investgo.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random


data class StockData(
    val icon: ImageVector? = null,
    val name: String,
    val stockPriceData: StockPriceData,
) {
    val history = mutableStateListOf<Float>()

    fun update() {
        history.add(stockPriceData.apply { update() }.price)
    }
}


data class StockPriceData(
    var trend: Float,
    var price: Float,
    var trendChangeRate: Float,
    var priceChangeRate: Float
) {
    fun update() {
        trend += (Random.nextFloat() * 2f - 0.996f) * trendChangeRate - trend * 0.01f
        price *= 1f + (trend + Random.nextFloat() * 2f - 0.996f) * priceChangeRate
    }
}
