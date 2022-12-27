@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.investgo.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.taeyeon.investgo.R
import com.taeyeon.investgo.data.Settings
import com.taeyeon.investgo.model.GameSubScreen
import com.taeyeon.investgo.model.GameViewModel
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.theme.gmarketSans
import com.taeyeon.investgo.util.formatPrice


@Composable
fun GameScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(mainViewModel.welcomeViewModel.userName, mainViewModel.readyForGameViewModel.selected) {
        mainViewModel.gameViewModel.endTimer()
        mainViewModel.gameViewModel = GameViewModel(
            name = mainViewModel.welcomeViewModel.userName.trim(),
            time = mainViewModel.readyForGameViewModel.timeList[mainViewModel.readyForGameViewModel.selected].first
        )
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size = it }
                .blur(
                    animateDpAsState(
                        targetValue =
                        if (mainViewModel.gameViewModel.isShowingMenu || mainViewModel.gameViewModel.isShowingEnding) 10.dp
                        else 0.dp
                    ).value
                )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    .padding(
                        vertical = 24.dp,
                        horizontal = 32.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        mainViewModel.gameViewModel.isShowingMenu =
                            !mainViewModel.gameViewModel.isShowingMenu
                    },
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = LocalContentColor.current,
                            shape = CircleShape
                        )
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "TODO"
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))

                Text(
                    text = mainViewModel.gameViewModel.remainingVisibleTime,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = LocalDensity.current.run { 24.dp.toSp() },
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .width(100.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )

                Text(
                    text = "⌈${mainViewModel.gameViewModel.name}⌋님의 자산: ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    fontSize = LocalDensity.current.run { 32.dp.toSp() },
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "${formatPrice(mainViewModel.gameViewModel.gameData.getScore().toInt())}원",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = LocalDensity.current.run { 32.dp.toSp() },
                    color = if (mainViewModel.gameViewModel.gameData.getScore().toInt() > Settings.DEFAULT_MONEY) Color.Red
                    else if (mainViewModel.gameViewModel.gameData.getScore().toInt() < Settings.DEFAULT_MONEY) Color.Blue
                    else MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "(${mainViewModel.gameViewModel.gameData.getScore() / Settings.DEFAULT_MONEY * 100}%)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Light,
                    fontSize = LocalDensity.current.run { 16.dp.toSp() },
                    color = if (mainViewModel.gameViewModel.gameData.getScore().toInt() > Settings.DEFAULT_MONEY) Color.Red
                    else if (mainViewModel.gameViewModel.gameData.getScore().toInt() < Settings.DEFAULT_MONEY) Color.Blue
                    else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(start = 4.dp)
                )

            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {


                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(state = rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        mainViewModel.gameViewModel.gameData.marketData.forEachIndexed { tradeCowIndex, tradeCowData ->
                            var isExpanded by rememberSaveable { mutableStateOf(false) }

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = LocalContentColor.current.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = LocalContentColor.current
                                ),
                                onClick = { isExpanded = !isExpanded }
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .padding(16.dp),
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .animateContentSize(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        CompositionLocalProvider(
                                            LocalContentColor provides if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                                        ) {

                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = tradeCowData.icon,
                                                    contentDescription = tradeCowData.name,
                                                    modifier = Modifier.size(48.dp)
                                                )
                                                Text(
                                                    text = tradeCowData.name,
                                                    fontFamily = gmarketSans,
                                                    fontSize = LocalDensity.current.run { 40.dp.toSp() },
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.offset(y = 2.dp)
                                                )
                                                Spacer(
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Icon(
                                                    imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                                                    contentDescription = "집 가고 싶다", // TODO
                                                    tint = LocalContentColor.current.copy(alpha = 0.5f),
                                                    modifier = Modifier.size(48.dp)
                                                )
                                            }

                                            if (isExpanded) {

                                                tradeCowData.stockDataList.forEachIndexed { stockDataIndex, stockData ->
                                                    Surface(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        color = LocalContentColor.current.copy(alpha = 0.6f),
                                                        shape = RoundedCornerShape(8.dp),
                                                        border = BorderStroke(
                                                            width = 2.dp,
                                                            color = LocalContentColor.current
                                                        ),
                                                        onClick = {
                                                            mainViewModel.gameViewModel.subScreen =
                                                                if (
                                                                    mainViewModel.gameViewModel.subScreen is GameSubScreen.Chart &&
                                                                    (mainViewModel.gameViewModel.subScreen as GameSubScreen.Chart).tradeCowIndex == tradeCowIndex &&
                                                                    (mainViewModel.gameViewModel.subScreen as GameSubScreen.Chart).stockDataIndex == stockDataIndex
                                                                ) GameSubScreen.Default
                                                                else GameSubScreen.Chart(
                                                                    tradeCowIndex = tradeCowIndex,
                                                                    stockDataIndex = stockDataIndex
                                                                )
                                                        }
                                                    ) {
                                                        CompositionLocalProvider(
                                                            LocalContentColor provides if (!isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                                                        ) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .height(80.dp)
                                                                    .padding(16.dp)
                                                            ) {
                                                                Text(
                                                                    text = stockData.name,
                                                                    fontFamily = gmarketSans,
                                                                    fontSize = LocalDensity.current.run { 20.dp.toSp() },
                                                                    fontWeight = FontWeight.Medium,
                                                                    modifier = Modifier.align(
                                                                        Alignment.CenterStart
                                                                    )
                                                                )
                                                                Row(
                                                                    modifier = Modifier.align(Alignment.TopEnd),
                                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                                    verticalAlignment = Alignment.Top
                                                                ) {
                                                                    if (mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first != 0) {
                                                                        Text(
                                                                            text = "${mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first}개 소유",
                                                                            fontFamily = gmarketSans,
                                                                            fontSize = LocalDensity.current.run { 10.dp.toSp() },
                                                                            fontWeight = FontWeight.Light,
                                                                            color = if (stockData.stockPriceData.price > mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].second) Color.Red
                                                                            else if (stockData.stockPriceData.price < mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].second) Color.Blue
                                                                            else LocalContentColor.current
                                                                        )
                                                                    }
                                                                    Text(
                                                                        text = "${stockData.stockPriceData.price}원",
                                                                        fontFamily = gmarketSans,
                                                                        fontSize = LocalDensity.current.run { 20.dp.toSp() },
                                                                        fontWeight = FontWeight.Medium,
                                                                        color = if (stockData.stockPriceData.trend > 0) Color.Red
                                                                        else if (stockData.stockPriceData.trend < 0) Color.Blue
                                                                        else LocalContentColor.current
                                                                    )
                                                                }
                                                                Row(
                                                                    modifier = Modifier.align(Alignment.BottomEnd),
                                                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                ) {
                                                                    Text(
                                                                        text = "추세: ${stockData.stockPriceData.trend}",
                                                                        fontFamily = gmarketSans,
                                                                        fontSize = LocalDensity.current.run { 12.dp.toSp() },
                                                                        fontWeight = FontWeight.Light,
                                                                        color =
                                                                            if (stockData.stockPriceData.trend > 0) Color.Red
                                                                            else if (stockData.stockPriceData.trend < 0) Color.Blue
                                                                            else LocalContentColor.current
                                                                    )
                                                                    Text(
                                                                        text = "가격 변동률: ${stockData.stockPriceData.priceChangeRate * 100f}%",
                                                                        fontFamily = gmarketSans,
                                                                        fontSize = LocalDensity.current.run { 12.dp.toSp() },
                                                                        fontWeight = FontWeight.Light
                                                                    )
                                                                    Text(
                                                                        text = "추세 변동률: ${stockData.stockPriceData.trendChangeRate * 100}%",
                                                                        fontFamily = gmarketSans,
                                                                        fontSize = LocalDensity.current.run { 12.dp.toSp() },
                                                                        fontWeight = FontWeight.Light
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            } else {

                                                val contentColor = LocalContentColor.current
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    for (stockIndex in 0 until tradeCowData.stockDataList.size.let { if (it > 3) 3 else it }) { // Only 3
                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.spacedBy(
                                                                4.dp
                                                            ),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Text(
                                                                text = tradeCowData.stockDataList[stockIndex].name,
                                                                fontFamily = gmarketSans,
                                                                fontSize = LocalDensity.current.run { 10.dp.toSp() },
                                                                fontWeight = FontWeight.Light
                                                            )
                                                            Canvas(
                                                                modifier = Modifier.weight(1f)
                                                            ) {
                                                                drawLine(
                                                                    color = contentColor,
                                                                    start = Offset(x = 0f, y = 0f),
                                                                    end = Offset(
                                                                        x = this.size.width,
                                                                        y = 0f
                                                                    ),
                                                                    strokeWidth = 1.dp.toPx(),
                                                                    cap = StrokeCap.Round,
                                                                    pathEffect = PathEffect.dashPathEffect(
                                                                        floatArrayOf(3f, 3f)
                                                                    )
                                                                )
                                                            }
                                                            Text(
                                                                text = "${tradeCowData.stockDataList[stockIndex].stockPriceData.price}원",
                                                                fontFamily = gmarketSans,
                                                                fontSize = LocalDensity.current.run { 10.dp.toSp() },
                                                                fontWeight = FontWeight.Medium,
                                                                color =
                                                                    if (tradeCowData.stockDataList[stockIndex].stockPriceData.trend > 0) Color.Red
                                                                    else if (tradeCowData.stockDataList[stockIndex].stockPriceData.trend < 0) Color.Blue
                                                                    else LocalContentColor.current
                                                            )
                                                        }
                                                    }
                                                }
                                                if (tradeCowData.stockDataList.size > 3) {
                                                    Canvas(
                                                        modifier = Modifier
                                                            .width(3.dp)
                                                            .height(12.dp)
                                                            .align(Alignment.CenterHorizontally)
                                                    ) {
                                                        for (drawIndex in 0..2) {
                                                            drawCircle(
                                                                color = contentColor.copy(alpha = 0.5f),
                                                                radius = 1.5f.dp.toPx(),
                                                                center = Offset(
                                                                    x = 1.5f.dp.toPx(),
                                                                    y = (1.5f + drawIndex * 4.5f).dp.toPx()
                                                                ),
                                                            )
                                                        }
                                                    }
                                                }

                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                AnimatedContent(
                    targetState = mainViewModel.gameViewModel.subScreen,
                    modifier = Modifier
                        .weight(
                            animateFloatAsState(
                                targetValue = mainViewModel.gameViewModel.subScreen.fraction * 2f
                            ).value
                        )
                        .fillMaxHeight()
                ) { screen ->
                    when (screen) {

                        GameSubScreen.Default -> {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    color = LocalContentColor.current.copy(alpha = 0.6f),
                                    contentColor = MaterialTheme.colorScheme.onBackground,
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = LocalContentColor.current
                                    )
                                ) {
                                    CompositionLocalProvider(
                                        LocalContentColor provides if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                        ) {
                                            val dotDivider: @Composable (width: Dp, alpha: Float) -> Unit =
                                                { width, alpha ->
                                                    val contentColor = LocalContentColor.current
                                                    Canvas(
                                                        modifier = Modifier
                                                            .padding(vertical = 8.dp)
                                                            .fillMaxWidth()
                                                            .height(width)
                                                    ) {
                                                        drawLine(
                                                            color = contentColor,
                                                            start = Offset(x = 0f, y = 0f),
                                                            end = Offset(
                                                                x = this.size.width,
                                                                y = 0f
                                                            ),
                                                            strokeWidth = width.toPx(),
                                                            pathEffect = PathEffect.dashPathEffect(
                                                                floatArrayOf(10f, 10f)
                                                            ),
                                                            alpha = alpha
                                                        )
                                                    }
                                                }

                                            val informationFontStyle =
                                                MaterialTheme.typography.labelMedium.copy(
                                                    fontSize = LocalDensity.current.run { 12.dp.toSp() },
                                                    fontWeight = FontWeight.Medium,
                                                    fontFamily = gmarketSans,
                                                    textAlign = TextAlign.Center
                                                )


                                            Text(
                                                text = "보유 자산",
                                                fontSize = LocalDensity.current.run { 40.dp.toSp() },
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = gmarketSans,
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.padding(8.dp)
                                            )

                                            Divider(
                                                thickness = 1.dp,
                                                modifier = Modifier
                                                    .padding(vertical = 8.dp)
                                                    .fillMaxWidth()
                                            )

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = "원",
                                                    fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                                    fontWeight = FontWeight.Medium,
                                                    fontFamily = gmarketSans,
                                                    textAlign = TextAlign.Start
                                                )
                                                Spacer(modifier = Modifier.weight(1f))
                                                Text(
                                                    text = mainViewModel.gameViewModel.gameData.won.toString(),
                                                    fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                                    fontWeight = FontWeight.Light,
                                                    fontFamily = gmarketSans,
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                            dotDivider(1.dp, 0.75f)

                                            var isPropertyEmpty by rememberSaveable { mutableStateOf(false) }
                                            LaunchedEffect(mainViewModel.gameViewModel.gameData.propertyData) {
                                                isPropertyEmpty = true
                                                for (tradeCow in mainViewModel.gameViewModel.gameData.propertyData) {
                                                    for (stockData in tradeCow) {
                                                        if (stockData.first != 0) isPropertyEmpty = false
                                                    }
                                                }
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)
                                                    .verticalScroll(state = rememberScrollState())
                                            ) {
                                                if (isPropertyEmpty) {
                                                    Text(
                                                        text = "다른 유형의 자산이 없습니다 :(",
                                                        fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                                        fontWeight = FontWeight.Medium,
                                                        fontFamily = gmarketSans,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                } else {
                                                    Column(
                                                        modifier = Modifier.fillMaxWidth()
                                                    ) {
                                                        mainViewModel.gameViewModel.gameData.propertyData.forEachIndexed { tradeCowIndex, tradeCow ->
                                                            var isTradeCowEmpty by rememberSaveable { mutableStateOf(true) }

                                                            LaunchedEffect(mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList) {
                                                                isTradeCowEmpty = true
                                                            }

                                                            if (!isTradeCowEmpty) {
                                                                Text(
                                                                    text = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].name,
                                                                    fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                                                    fontWeight = FontWeight.Medium,
                                                                    fontFamily = gmarketSans,
                                                                    textAlign = TextAlign.Start
                                                                )
                                                                dotDivider(0.5f.dp, 0.5f)
                                                            }
                                                            tradeCow.forEachIndexed { stockIndex, stock ->
                                                                if (stock.first != 0) {
                                                                    val stockData = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockIndex]

                                                                    LaunchedEffect(stock.second) {
                                                                        if (stock.second > 0) isTradeCowEmpty = false
                                                                    }

                                                                    Row(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Text(
                                                                            text = stockData.name,
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = (stockData.stockPriceData.price / stock.second * 100).toString(),
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = stock.second.toString(),
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = stockData.stockPriceData.price.toString(),
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = stock.first.toString(),
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                        Text(
                                                                            text = (stock.second * stockData.stockPriceData.price).toString(),
                                                                            style = informationFontStyle,
                                                                            modifier = Modifier.weight(1f)
                                                                        )
                                                                    }
                                                                    dotDivider(0.5f.dp, 0.5f)
                                                                }
                                                            }
                                                            if (!isTradeCowEmpty) {
                                                                dotDivider(1.dp, 0.75f)
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            Divider(
                                                thickness = 1.dp,
                                                modifier = Modifier
                                                    .padding(vertical = 8.dp)
                                                    .fillMaxWidth()
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(32.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = "종목 이름",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "변화 비율",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "구매 가격",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "현재 가격",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "보유 개수",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    Text(
                                                        text = "보유량",
                                                        style = informationFontStyle,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                            }

                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                Button(
                                    onClick = {
                                        for (tradeCowIndex in mainViewModel.gameViewModel.gameData.propertyData.indices) {
                                            for (stockDataIndex in mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex].indices) {
                                                mainViewModel.gameViewModel.gameData.won += mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].stockPriceData.price * mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first
                                                mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex] = 0 to 0f
                                            }
                                        }
                                    },
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    enabled = !mainViewModel.gameViewModel.isStoped,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "모두 판매하기",
                                        fontSize = with(LocalDensity.current) { 36.dp.toSp() },
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }

                        is GameSubScreen.Chart -> {
                            val tradeCowIndex = screen.tradeCowIndex
                            val stockDataIndex = screen.stockDataIndex

                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Chart(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(3f),
                                    icon = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].icon,
                                    stockData = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex],
                                    toolbarLeftItem = {
                                        val tint = LocalContentColor.current
                                        OutlinedButton(
                                            onClick = { mainViewModel.gameViewModel.subScreen = GameSubScreen.Default },
                                            shape = CircleShape,
                                            contentPadding = PaddingValues(2.dp),
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Close,
                                                contentDescription = "닫기", // TODO
                                                tint = tint
                                            )
                                        }
                                    },
                                    averagePurchasePrice = mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].second.let {
                                        if (it == 0f || mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first == 0) null
                                        else it
                                    }
                                )
                                
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val price = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].stockPriceData.price
                                    val won = mainViewModel.gameViewModel.gameData.won

                                    val isBuyingAble = mainViewModel.gameViewModel.gameData.won >= mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].stockPriceData.price
                                    var isBuyingEnabled by rememberSaveable { mutableStateOf(false) }
                                    val isSellingAble = mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first > 0
                                    var isSellingEnabled by rememberSaveable { mutableStateOf(false) }

                                    var wonValue by rememberSaveable { mutableStateOf(0) }
                                    var stockValue by rememberSaveable { mutableStateOf(0) }


                                    Column(
                                        modifier = Modifier
                                            .weight(1.6f)
                                            .background(
                                                color = LocalContentColor.current.copy(alpha = 0.05f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        val steps = (won / price).toInt()

                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "원",
                                                fontFamily = gmarketSans,
                                                fontSize = LocalDensity.current.run { 16.dp.toSp() },
                                                fontWeight = FontWeight.Medium
                                            )
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                text = "$won",
                                                fontFamily = gmarketSans,
                                                fontSize = LocalDensity.current.run { 16.dp.toSp() },
                                                fontWeight = FontWeight.Light
                                            )
                                        }
                                        Text(
                                            text = "${price * wonValue}",
                                            fontFamily = gmarketSans,
                                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    width = 1.dp,
                                                    color = LocalContentColor.current,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(
                                                    vertical = 16.dp,
                                                    horizontal = 4.dp
                                                )
                                        )
                                        if (isBuyingAble) {
                                            Slider(
                                                value = wonValue.toFloat(),
                                                onValueChange = {
                                                    isBuyingEnabled = true
                                                    isSellingEnabled = false
                                                    wonValue = it.toInt()
                                                },
                                                valueRange = 0f .. steps.toFloat(),
                                                steps = steps,
                                                colors = if (isBuyingEnabled) SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.primary,
                                                    activeTrackColor = MaterialTheme.colorScheme.primary,
                                                    activeTickColor = MaterialTheme.colorScheme.primary,
                                                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                                                    inactiveTickColor = MaterialTheme.colorScheme.surfaceVariant,
                                                    disabledThumbColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface
                                                )
                                                else SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.onSurface,
                                                    activeTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    activeTickColor = MaterialTheme.colorScheme.onSurface,
                                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    inactiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledThumbColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface
                                                ),
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                mainViewModel.gameViewModel.gameData.won -= price * wonValue
                                                mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex] =
                                                    mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first + wonValue to
                                                            (mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].second * mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first + price * wonValue) /
                                                            (mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first + wonValue)

                                                isBuyingEnabled = mainViewModel.gameViewModel.gameData.won >= mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].stockPriceData.price
                                                isSellingEnabled = mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first > 0

                                                wonValue = 0
                                                stockValue = 0
                                            },
                                            shape = MaterialTheme.shapes.medium,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary,
                                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                            ),
                                            enabled = isBuyingAble && isBuyingEnabled && !mainViewModel.gameViewModel.isStoped,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowRight,
                                                contentDescription = "화살표", // TODO
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Text(
                                                text = "구매하기",
                                                fontSize = with(LocalDensity.current) { 20.dp.toSp() },
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowRight,
                                                contentDescription = "화살표", // TODO
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                        Button(
                                            onClick = {
                                                mainViewModel.gameViewModel.gameData.won += price * stockValue
                                                mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex] =
                                                    mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first - stockValue to
                                                            if (mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first - stockValue == 0) 0f else mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].second

                                                isBuyingEnabled = mainViewModel.gameViewModel.gameData.won >= mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].stockPriceData.price
                                                isSellingEnabled = mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first > 0

                                                wonValue = 0
                                                stockValue = 0
                                            },
                                            shape = MaterialTheme.shapes.medium,
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = MaterialTheme.colorScheme.primary,
                                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                            ),
                                            enabled = isSellingAble && isSellingEnabled && !mainViewModel.gameViewModel.isStoped,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowLeft,
                                                contentDescription = "화살표", // TODO
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Text(
                                                text = "판매하기",
                                                fontSize = with(LocalDensity.current) { 20.dp.toSp() },
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                imageVector = Icons.Rounded.KeyboardArrowLeft,
                                                contentDescription = "화살표", // TODO
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }

                                    Column(
                                        modifier = Modifier
                                            .weight(1.6f)
                                            .background(
                                                color = LocalContentColor.current.copy(alpha = 0.05f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        val stockNumber = mainViewModel.gameViewModel.gameData.propertyData[tradeCowIndex][stockDataIndex].first

                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "$stockNumber",
                                                fontFamily = gmarketSans,
                                                fontSize = LocalDensity.current.run { 16.dp.toSp() },
                                                fontWeight = FontWeight.Light
                                            )
                                            Spacer(Modifier.weight(1f))
                                            Text(
                                                text = mainViewModel.gameViewModel.gameData.marketData[tradeCowIndex].stockDataList[stockDataIndex].name,
                                                fontFamily = gmarketSans,
                                                fontSize = LocalDensity.current.run { 16.dp.toSp() },
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        Text(
                                            text = stockValue.toString(),
                                            fontFamily = gmarketSans,
                                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    width = 1.dp,
                                                    color = LocalContentColor.current,
                                                    shape = RoundedCornerShape(4.dp)
                                                )
                                                .padding(
                                                    vertical = 16.dp,
                                                    horizontal = 4.dp
                                                )
                                        )
                                        if (isSellingAble) {
                                            Slider(
                                                value = stockValue.toFloat(),
                                                onValueChange = {
                                                    isSellingEnabled = true
                                                    isBuyingEnabled = false
                                                    stockValue = it.toInt()
                                                },
                                                valueRange = 0f .. stockNumber.toFloat(),
                                                steps = stockNumber,
                                                colors = if (isSellingEnabled) SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.primary,
                                                    activeTrackColor = MaterialTheme.colorScheme.primary,
                                                    activeTickColor = MaterialTheme.colorScheme.primary,
                                                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                                                    inactiveTickColor = MaterialTheme.colorScheme.surfaceVariant,
                                                    disabledThumbColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface
                                                )
                                                else SliderDefaults.colors(
                                                    thumbColor = MaterialTheme.colorScheme.onSurface,
                                                    activeTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    activeTickColor = MaterialTheme.colorScheme.onSurface,
                                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    inactiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledThumbColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledActiveTickColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTrackColor = MaterialTheme.colorScheme.onSurface,
                                                    disabledInactiveTickColor = MaterialTheme.colorScheme.onSurface
                                                ),
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }

                                }

                            }
                        }

                    }
                }


            }

        }


        Box(
            modifier = Modifier
                .width(
                    if (mainViewModel.gameViewModel.isShowingMenu) LocalDensity.current.run { size.width.toDp() }
                    else 0.dp
                )
                .height(
                    if (mainViewModel.gameViewModel.isShowingMenu) LocalDensity.current.run { size.height.toDp() }
                    else 0.dp
                )
                .background(
                    color = animateColorAsState(
                        targetValue =
                        if (mainViewModel.gameViewModel.isShowingMenu) MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.4f
                        )
                        else Color.Transparent,
                        animationSpec = tween(durationMillis = 1000)
                    ).value
                )
                .pointerInput(Unit) {
                    detectTapGestures {
                        mainViewModel.gameViewModel.isShowingMenu = false
                    }
                }
        ) {

            LaunchedEffect(mainViewModel.gameViewModel.isShowingMenu) {
                if (mainViewModel.gameViewModel.isShowingMenu) mainViewModel.gameViewModel.stopTimer()
                else mainViewModel.gameViewModel.resumeTimer()
            }

            if (mainViewModel.gameViewModel.isShowingMenu) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(LocalDensity.current.run { size.width.toDp() * 0.4f })
                        .height(LocalDensity.current.run { size.height.toDp() * 0.6f }),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = with(LocalDensity.current) { 80.dp.toSp() },
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "일시 중지",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                            fontWeight = FontWeight.Bold
                        )
                    }

                    listOf(
                        Triple(Icons.Rounded.PlayArrow, "게임 계속하기") {
                            mainViewModel.gameViewModel.isShowingMenu = false
                            mainViewModel.gameViewModel.resumeTimer()
                        },
                        Triple(Icons.Rounded.Redo, "게임 다시 시작하기") {
                            mainViewModel.gameViewModel.endTimer()
                            mainViewModel.gameViewModel = GameViewModel(
                                name = mainViewModel.welcomeViewModel.userName.trim(),
                                time = mainViewModel.readyForGameViewModel.timeList[mainViewModel.readyForGameViewModel.selected].first
                            )
                        },
                        Triple(Icons.Rounded.Close, "게임 그만하기") {
                            mainViewModel.gameViewModel.endTimer()
                        }
                    ).forEach {
                        Button(
                            onClick = it.third,
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                width = 4.dp,
                                color = LocalContentColor.current
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                                    .compositeOver(MaterialTheme.colorScheme.primary),
                                contentColor = LocalContentColor.current,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var iconSize by remember { mutableStateOf(IntSize.Zero) }
                                Icon(
                                    imageVector = it.first,
                                    contentDescription = it.second,
                                    modifier = Modifier
                                        .width(LocalDensity.current.run { iconSize.height.toDp() })
                                        .fillMaxHeight()
                                        .onSizeChanged { intSize -> iconSize = intSize }
                                )
                                Text(
                                    text = it.second,
                                    fontSize = with(LocalDensity.current) { 24.dp.toSp() },
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                } // TODO

                Text(
                    text = "2022 동산제 - 인공지능컴퓨터동아리 부스 (개발자: 20616 정태연)",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                ) // TODO

            }


            if (mainViewModel.gameViewModel.isShowingEnding) {

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(LocalDensity.current.run { size.width.toDp() * 0.4f })
                        .height(LocalDensity.current.run { size.height.toDp() * 0.6f }),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = with(LocalDensity.current) { 80.dp.toSp() },
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "일시 중지",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                            fontWeight = FontWeight.Bold
                        )
                    }

                    listOf(
                        Triple(Icons.Rounded.PlayArrow, "게임 계속하기") {
                            mainViewModel.gameViewModel.isShowingMenu = false
                            mainViewModel.gameViewModel.resumeTimer()
                        },
                        Triple(Icons.Rounded.Redo, "게임 다시 시작하기") {
                            mainViewModel.gameViewModel.endTimer()
                            mainViewModel.gameViewModel = GameViewModel(
                                name = mainViewModel.welcomeViewModel.userName.trim(),
                                time = mainViewModel.readyForGameViewModel.timeList[mainViewModel.readyForGameViewModel.selected].first
                            )
                        },
                        Triple(Icons.Rounded.Close, "게임 그만하기") {
                            mainViewModel.gameViewModel.endTimer()
                        }
                    ).forEach {
                        Button(
                            onClick = it.third,
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(
                                width = 4.dp,
                                color = LocalContentColor.current
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                                    .compositeOver(MaterialTheme.colorScheme.primary),
                                contentColor = LocalContentColor.current,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                var iconSize by remember { mutableStateOf(IntSize.Zero) }
                                Icon(
                                    imageVector = it.first,
                                    contentDescription = it.second,
                                    modifier = Modifier
                                        .width(LocalDensity.current.run { iconSize.height.toDp() })
                                        .fillMaxHeight()
                                        .onSizeChanged { intSize -> iconSize = intSize }
                                )
                                Text(
                                    text = it.second,
                                    fontSize = with(LocalDensity.current) { 24.dp.toSp() },
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                } // TODO

                Text(
                    text = "2022 동산제 - 인공지능컴퓨터동아리 부스 (개발자: 20616 정태연)",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                ) // TODO

            }

        }

    }
}