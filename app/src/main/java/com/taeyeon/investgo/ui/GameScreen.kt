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
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.taeyeon.investgo.R
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

    LaunchedEffect(mainViewModel.readyForGameViewModel.selected) {
        mainViewModel.gameViewModel.endTimer()
        mainViewModel.gameViewModel = GameViewModel(
            name = mainViewModel.welcomeViewModel.userName.trim(),
            time = mainViewModel.readyForGameViewModel.timeList[mainViewModel.readyForGameViewModel.selected].first,
            onEnd = {  }
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
                        if (mainViewModel.gameViewModel.isShowingMenu) 10.dp
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
                    color = MaterialTheme.colorScheme.onBackground
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
                        mainViewModel.gameViewModel.gameData.marketData.forEach { tradeCowData ->
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

                                                tradeCowData.stockDataList.forEach { stockData ->
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
                                                                if (mainViewModel.gameViewModel.subScreen is GameSubScreen.Chart && (mainViewModel.gameViewModel.subScreen as GameSubScreen.Chart).stockData == stockData) GameSubScreen.Default
                                                                else GameSubScreen.Chart(
                                                                    icon = tradeCowData.icon,
                                                                    stockData = stockData
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
                                                                Text(
                                                                    text = "${stockData.stockPriceData.price}원",
                                                                    fontFamily = gmarketSans,
                                                                    fontSize = LocalDensity.current.run { 20.dp.toSp() },
                                                                    fontWeight = FontWeight.Medium,
                                                                    color =
                                                                        if (stockData.stockPriceData.trend > 0) Color.Red
                                                                        else if (stockData.stockPriceData.trend < 0) Color.Blue
                                                                        else LocalContentColor.current,
                                                                    modifier = Modifier.align(
                                                                        Alignment.TopEnd
                                                                    )
                                                                )
                                                                Row(
                                                                    modifier = Modifier.align(
                                                                        Alignment.BottomEnd
                                                                    ),
                                                                    horizontalArrangement = Arrangement.spacedBy(
                                                                        4.dp
                                                                    ),
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
                                                                        text = "추세 변동률: ${stockData.stockPriceData.trendChangeRate * 100f}%",
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
                ) {
                    when (it) {

                        GameSubScreen.Default -> {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                var isActionShowing by rememberSaveable { mutableStateOf(false) }

                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    color = LocalContentColor.current.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = LocalContentColor.current
                                    )
                                ) {
                                    //
                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                Button(
                                    onClick = { isActionShowing = !isActionShowing },
                                    shape = MaterialTheme.shapes.medium,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .alpha(0.6f)
                                ) {
                                    Icon(
                                        imageVector = if (isActionShowing) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = "집 가고 싶다", // TODO
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Text(
                                        text = if (isActionShowing) "액션 숨기기" else "액션 보이기",
                                        fontSize = with(LocalDensity.current) { 24.dp.toSp() },
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        imageVector = if (isActionShowing) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = "집 가고 싶다", // TODO
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Spacer(
                                    modifier = Modifier
                                        .height(
                                            animateDpAsState(
                                                targetValue = if (isActionShowing) 16.dp else 0.dp
                                            ).value
                                        )
                                )

                                AnimatedVisibility(visible = isActionShowing) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        listOf(
                                            "돈 벌러가기" to { /*TODO*/ },
                                            "모두 판매하기" to {}
                                        ).forEach { pair ->
                                            Button(
                                                onClick = pair.second,
                                                shape = MaterialTheme.shapes.medium,
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = MaterialTheme.colorScheme.primary,
                                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = pair.first,
                                                    fontSize = with(LocalDensity.current) { 36.dp.toSp() },
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }

                        is GameSubScreen.Chart -> {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Chart(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(2.5f),
                                    icon = it.icon,
                                    stockData = it.stockData,
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
                                    }
                                )
                                
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "대충 거래",
                                        fontFamily = gmarketSans,
                                        fontSize = LocalDensity.current.run { 64.dp.toSp() },
                                        fontWeight = FontWeight.Bold
                                    )
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

            if (mainViewModel.gameViewModel.isShowingMenu) {

                LaunchedEffect(mainViewModel.gameViewModel.isShowingMenu) {
                    mainViewModel.gameViewModel.stopTimer()
                }

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
                        Triple(Icons.Rounded.Redo, "게임 다시 시작하기") { // TODO
                        },
                        Triple(Icons.Rounded.Close, "게임 그만하기") {}
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