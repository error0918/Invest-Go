package com.taeyeon.investgo.data

data class GameData(
    val marketData: List<TradeCowData> = ArrayList(com.taeyeon.investgo.data.marketData),
    val propertyData: ArrayList<ArrayList<Int>> = marketData.let {
        val outerArrayList = arrayListOf<ArrayList<Int>>()
        it.forEach { tradeCowData ->
            val innerArrayList = arrayListOf<Int>()
            for (index in tradeCowData.stockDataList.indices) innerArrayList.add(0)
            outerArrayList.add(innerArrayList)
        }
        outerArrayList
    },
    var won: Float = Settings.DEFAULT_MONEY
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
