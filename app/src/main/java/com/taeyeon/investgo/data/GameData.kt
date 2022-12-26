package com.taeyeon.investgo.data

data class GameData(
    val marketData: List<TradeCowData> = ArrayList(com.taeyeon.investgo.data.marketData),
    var won: Int = Settings.DEFAULT_MONEY,
    val propertyData: List<List<Int>> = marketData.let {
        val arrayList = arrayListOf<List<Int>>()
        it.forEach { tradeCowData -> arrayList.add(List(tradeCowData.stockDataList.size) { 0 }) }
        arrayList.toList()
    }
) {
    fun getScore(): Float {
        var score = won.toFloat()
        for (outerIndex in propertyData.indices) {
            for (innerIndex in propertyData[outerIndex].indices) {
                score += marketData[outerIndex].stockDataList[innerIndex].stockPriceData.price * propertyData[outerIndex][innerIndex]
            }
        }
        return score
    }
}
