package com.taeyeon.investgo.data

data class GameData(
    val marketData: List<TradeCowData> = ArrayList(com.taeyeon.investgo.data.marketData),
    val propertyData: ArrayList<ArrayList<Pair<Int, Float>>> = marketData.let {
        val outerArrayList = arrayListOf<ArrayList<Pair<Int, Float>>>()
        it.forEach { tradeCowData ->
            val innerArrayList = arrayListOf<Pair<Int, Float>>()
            for (index in tradeCowData.stockDataList.indices) innerArrayList.add(0 to 0f)
            outerArrayList.add(innerArrayList)
        }
        outerArrayList
    }, // 개수 to 평균 단가
    var won: Float = Settings.DEFAULT_MONEY
) {
    fun getScore(): Float {
        var score = won.toFloat()
        for (outerIndex in propertyData.indices) {
            for (innerIndex in propertyData[outerIndex].indices) {
                score += marketData[outerIndex].stockDataList[innerIndex].stockPriceData.price * propertyData[outerIndex][innerIndex].first
            }
        }
        return score
    }
}
