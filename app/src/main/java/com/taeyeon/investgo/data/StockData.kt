package com.taeyeon.investgo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random


val marketData =
    listOf(

        TradeCowData(
            icon = Icons.Rounded.Euro,
            name = "외화 거래소",
            stockDataList = listOf(
                StockData(
                    name = "미국 달러",
                    stockPriceData = StockPriceData(
                        trend = 0.03f,
                        price = 1300f,
                        trendChangeRate = 0.003f,
                        priceChangeRate = 0.003f
                    )
                ),
                StockData(
                    name = "중국 위안",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 180f,
                        trendChangeRate = 0.005f,
                        priceChangeRate = 0.005f
                    )
                ),
                StockData(
                    name = "일본 엔",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 9.6f,
                        trendChangeRate = 0.001f,
                        priceChangeRate = 0.005f
                    )
                ),
                StockData(
                    name = "유럽연합 유로",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 1350f,
                        trendChangeRate = 0.0005f,
                        priceChangeRate = 0.005f
                    )
                ),
                StockData(
                    name = "영국 파운드",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 1600f,
                        trendChangeRate = 0.0005f,
                        priceChangeRate = 0.01f
                    )
                ),
                StockData(
                    name = "스위스 프랑",
                    stockPriceData = StockPriceData(
                        trend = 0.03f,
                        price = 1300f,
                        trendChangeRate = 0.0005f,
                        priceChangeRate = 0.005f
                    )
                ),
                StockData(
                    name = "인도 루피",
                    stockPriceData = StockPriceData(
                        trend = -0.03f,
                        price = 15f,
                        trendChangeRate = 0.001f,
                        priceChangeRate = 0.005f
                    )
                )
            )
        ),

        TradeCowData(
            icon = Icons.Rounded.OilBarrel,
            name = "원자재 거래소",
            stockDataList = listOf(
                StockData(
                    name = "금",
                    stockPriceData = StockPriceData(
                        trend = 0.02f,
                        price = 1800f,
                        trendChangeRate = 0.0001f,
                        priceChangeRate = 0.0001f
                    )
                ),
                StockData(
                    name = "은",
                    stockPriceData = StockPriceData(
                        trend = 0.01f,
                        price = 24f,
                        trendChangeRate = 0.003f,
                        priceChangeRate = 0.003f
                    )
                ),
                StockData(
                    name = "구리",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 5000f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.003f
                    )
                ),
                StockData(
                    name = "빽금",
                    stockPriceData = StockPriceData(
                        trend = 0.05f,
                        price = 1035f,
                        trendChangeRate = 0.005f,
                        priceChangeRate = 0.01f
                    )
                ),
                StockData(
                    name = "석유",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 1600f,
                        trendChangeRate = 0.0005f,
                        priceChangeRate = 0.01f
                    )
                ),
                StockData(
                    name = "천연가스",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 10270f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.03f
                    )
                )
            )
        ),

        TradeCowData(
            icon = Icons.Rounded.Money,
            name = "국내 주식",
            stockDataList = listOf(
                StockData(
                    name = "오성전자",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 58000f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.02f
                    )
                ),
                StockData(
                    name = "럭키별전자",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 88000f,
                        trendChangeRate = 0.03f,
                        priceChangeRate = 0.03f
                    )
                ),
                StockData(
                    name = "근대자동차",
                    stockPriceData = StockPriceData(
                        trend = -0.03f,
                        price = 15800f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.04f
                    )
                ),
                StockData(
                    name = "NK하이테크",
                    stockPriceData = StockPriceData(
                        trend = -0.03f,
                        price = 77000f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.07f
                    )
                ),
                StockData(
                    name = "NEIGHBOR",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 17500f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.02f
                    )
                ),
                StockData(
                    name = "MEETA",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 15300f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.01f
                    )
                ),
                StockData(
                    name = "쵸코",
                    stockPriceData = StockPriceData(
                        trend = 0.005f,
                        price = 5300f,
                        trendChangeRate = 0.01f,
                        priceChangeRate = 0.04f
                    )
                ),
                StockData(
                    name = "K-Oil",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 8900f,
                        trendChangeRate = 0.005f,
                        priceChangeRate = 0.01f
                    )
                )
            )
        ),

        TradeCowData(
            icon = Icons.Rounded.Money,
            name = "해외 주식",
            stockDataList = listOf(
                StockData(
                    name = "Pineapple",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 17000f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.03f
                    )
                ),
                StockData(
                    name = "Softmic",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 31200f,
                        trendChangeRate = 0.04f,
                        priceChangeRate = 0.04f
                    )
                ),
                StockData(
                    name = "Googole",
                    stockPriceData = StockPriceData(
                        trend = -0.03f,
                        price = 10000f,
                        trendChangeRate = 0.06f,
                        priceChangeRate = 0.03f
                    )
                ),
                StockData(
                    name = "MVIDIA",
                    stockPriceData = StockPriceData(
                        trend = 0.01f,
                        price = 19500f,
                        trendChangeRate = 0.06f,
                        priceChangeRate = 0.02f
                    )
                ),
                StockData(
                    name = "MEETA",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 15300f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.01f
                    )
                ),
                StockData(
                    name = "NETFLEX",
                    stockPriceData = StockPriceData(
                        trend = 0.02f,
                        price = 30900f,
                        trendChangeRate = 0.02f,
                        priceChangeRate = 0.06f
                    )
                )
            )
        ),

        TradeCowData(
            icon = Icons.Rounded.CurrencyBitcoin,
            name = "암호화폐",
            stockDataList = listOf(
                StockData(
                    name = "비트를 쪼개는 코인",
                    stockPriceData = StockPriceData(
                        trend = -0.01f,
                        price = 21900f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.06f
                    )
                ),
                StockData(
                    name = "플리",
                    stockPriceData = StockPriceData(
                        trend = -0.04f,
                        price = 45f,
                        trendChangeRate = 0.07f,
                        priceChangeRate = 0.04f
                    )
                ),
                StockData(
                    name = "삼더리움",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 1500f,
                        trendChangeRate = 0.03f,
                        priceChangeRate = 0.05f
                    )
                ),
                StockData(
                    name = "시츄코인",
                    stockPriceData = StockPriceData(
                        trend = 0f,
                        price = 10f,
                        trendChangeRate = 0.08f,
                        priceChangeRate = 0.06f
                    )
                ),
                StockData(
                    name = "불독코인",
                    stockPriceData = StockPriceData(
                        trend = -0.05f,
                        price = 0.2f,
                        trendChangeRate = 0.03f,
                        priceChangeRate = 0.07f
                    )
                ),
                StockData(
                    name = "모래상자",
                    stockPriceData = StockPriceData(
                        trend = -0.02f,
                        price = 56f,
                        trendChangeRate = 0.03f,
                        priceChangeRate = 0.03f
                    )
                )
            )
        ),

        TradeCowData(
            icon = Icons.Rounded.Warning,
            name = "위험 거래소",
            stockDataList = listOf(
                StockData(
                    name = "Confused.Inc",
                    stockPriceData = StockPriceData(
                        trend = -0.2f,
                        price = 100f,
                        trendChangeRate = 0.3f,
                        priceChangeRate = 0.3f
                    )
                ),
                StockData(
                    name = "UpDown Electronics",
                    stockPriceData = StockPriceData(
                        trend = -0.3f,
                        price = 200f,
                        trendChangeRate = 0.5f,
                        priceChangeRate = 0.05f
                    )
                ),
                StockData(
                    name = "신자본 ETF",
                    stockPriceData = StockPriceData(
                        trend = -0.25f,
                        price = 1100f,
                        trendChangeRate = 0.05f,
                        priceChangeRate = 0.5f
                    )
                )
            )
        )

    )


data class TradeCowData(
    val icon: ImageVector,
    val name: String,
    val stockDataList: List<StockData>
)


data class StockData(
    val name: String,
    val stockPriceData: StockPriceData,
) {
    val history = mutableStateListOf<Float>()

    var enabled = true

    fun update() {
        if (enabled) history.add(stockPriceData.apply { update() }.price)
        else history.add(stockPriceData.price)
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
