@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.investgo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyBitcoin
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.taeyeon.investgo.data.Screen
import com.taeyeon.investgo.data.StockData
import com.taeyeon.investgo.data.StockPriceData
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.theme.InvestGoTheme
import com.taeyeon.investgo.theme.gmarketSans
import com.taeyeon.investgo.ui.GameScreen
import com.taeyeon.investgo.ui.ReadyForGameScreen
import com.taeyeon.investgo.ui.WelcomeScreen
import kotlinx.coroutines.delay
import kotlin.math.ceil
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            InvestGoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel = MainViewModel(this)
                    AnimatedNavHost(
                        navController = mainViewModel.navHostController,
                        startDestination = Screen.Welcome.name
                    ) {
                        composable(
                            route = Screen.Welcome.name,
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            }
                        ) {
                            WelcomeScreen(mainViewModel = mainViewModel)
                        }
                        composable(
                            route = Screen.ReadyForGame.name,
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                        ) {
                            ReadyForGameScreen(
                                mainViewModel = mainViewModel
                            )
                        }
                        composable(
                            route = Screen.Game.name,
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                            popExitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(durationMillis = 1000)
                                )
                            },
                        ) {
                            GameScreen(
                                mainViewModel = mainViewModel
                            )
                        }
                    }

                    // Test()

                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            val decorView = window.decorView
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}


@Composable
fun Test(
    modifier: Modifier = Modifier
) {
    // Parameter
    val stockData by remember {
        mutableStateOf(
            StockData(
                icon = Icons.Rounded.CurrencyBitcoin,
                name = "이둬뤼움 (\\)",
                stockPriceData = StockPriceData(
                    trend = 0.02f,
                    price = 600000f,
                    trendChangeRate = 0.003f,
                    priceChangeRate = 0.05f
                )
            ).apply {
                update()
                update()
            }
        )
    }
    Log.e("PR", "tr: ${stockData.stockPriceData.trend} / pr: ${stockData.stockPriceData.price}")


    LaunchedEffect(true) {
        while (true) {
            delay(100)
            stockData.update()
        }
    }


    // Additional Stock Data
    var priceRange = stockData.history[0] .. stockData.history[0]
    stockData.history.forEach {
        if (it < priceRange.start) priceRange = it .. priceRange.endInclusive
        else if (it > priceRange.endInclusive) priceRange = priceRange.start .. it
    }

    val getRate = { price: Float -> (priceRange.endInclusive - price) / (priceRange.endInclusive - priceRange.start) }
    val changeAmount = stockData.history.last() - stockData.history[if (stockData.history.size == 0) 0 else stockData.history.size - 2]


    // Composable Variable
    var interval by rememberSaveable { mutableStateOf(1f) }
    var autoScroll by rememberSaveable { mutableStateOf(true) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Surface(
            color =
                if (!isSystemInDarkTheme()) Color.LightGray
                else Color.DarkGray,
            contentColor =
                if (isSystemInDarkTheme()) Color.LightGray
                else Color.DarkGray,
            border = BorderStroke(
                width = 4.dp,
                color = LocalContentColor.current
            ),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                
                Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(32.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1초",
                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                            fontFamily = gmarketSans
                        )
                        Switch(
                            checked = interval == 10f,
                            onCheckedChange = {
                                interval = if (interval == 1f) 10f else 1f
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = LocalContentColor.current,
                                checkedTrackColor = LocalContentColor.current.copy(alpha = 0.5f),
                                checkedBorderColor = LocalContentColor.current.copy(alpha = 0.5f),
                                checkedIconColor = LocalContentColor.current,
                                uncheckedThumbColor = LocalContentColor.current,
                                uncheckedTrackColor = LocalContentColor.current.copy(alpha = 0.5f),
                                uncheckedBorderColor = LocalContentColor.current.copy(alpha = 0.5f),
                                uncheckedIconColor = LocalContentColor.current,
                            ),
                            modifier = Modifier.height(24.dp)
                        )
                        Text(
                            text = "10초",
                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                            fontFamily = gmarketSans
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(32.dp)
                            .align(Alignment.Center),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        stockData.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = stockData.name,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        }
                        Text(
                            text = stockData.name, // TODO
                            fontSize = LocalDensity.current.run { 32.dp.toSp() },
                            fontWeight = FontWeight.Bold,
                            fontFamily = gmarketSans
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            checked = autoScroll,
                            onCheckedChange = { autoScroll = it },
                            modifier = Modifier.height(24.dp)
                        )
                        Text(
                            text = "자동 스크롤",
                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                            fontFamily = gmarketSans
                        )
                    }
                    
                }


                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    shape = CircleShape,
                    content = {  }
                )


                var chartSize by remember { mutableStateOf(IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { chartSize = it }
                ) {

                    val scrollState = rememberScrollState()
                    val oneBlock = LocalDensity.current.run {
                        (chartSize.width.toDp() - (64.dp + 2.dp)) * 0.1f to chartSize.height.toDp() - (32.dp + 16.dp + 16.dp)
                    }

                    val density = LocalDensity.current
                    LaunchedEffect(changeAmount) {
                        if (autoScroll && scrollState.canScrollForward)
                            scrollState.animateScrollTo(
                                value = scrollState.value + density.run { oneBlock.first.toPx() / (interval * 10f) }.roundToInt(),
                                animationSpec = tween(80, easing = LinearEasing)
                            )
                    }

                    Row(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                end = 64.dp + 2.dp,
                                bottom = 32.dp + 16.dp
                            )
                            .fillMaxSize()
                            .horizontalScroll(state = scrollState)
                    ) {
                        val primary = MaterialTheme.colorScheme.primary
                        Canvas(
                            modifier = Modifier
                                .width(oneBlock.first * ((stockData.history.size) / (interval * 10) + (if (stockData.history.size >= interval * 100f) 1f else 0.5f)))
                                .fillMaxHeight()
                        ) {
                            val path = Path().apply {
                                var x = 0f

                                moveTo(x = x, y = oneBlock.second.toPx() * getRate(stockData.history.first()))

                                x += oneBlock.first.toPx() * 0.5f
                                for (index in 0 until stockData.history.size) {
                                    x += oneBlock.first.toPx() / (interval * 10)
                                    lineTo(x = x, y = oneBlock.second.toPx() * getRate(stockData.history[index]))
                                }

                                if (stockData.history.size >= interval * 100) x += oneBlock.first.toPx() * 0.5f
                                lineTo(x = x, y = oneBlock.second.toPx() * getRate(stockData.history.last()))
                            }

                            drawPath(
                                path = path,
                                color = primary,
                                style = Stroke(
                                    width = 4.dp.toPx()
                                )
                            )

                            drawPath(
                                path = path.apply {
                                    lineTo(x = size.width, y = size.height)
                                    lineTo(x = 0f, y = size.height)
                                    close()
                                },
                                color = primary.copy(alpha = 0.2f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(end = 64.dp + 2.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .horizontalScroll(state = scrollState)
                    ) {
                        for (index in 0 .. (ceil(stockData.history.size / (interval * 10)).toInt())) {
                            Text(
                                text = (index * interval).toInt().toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(oneBlock.first)
                            )
                        }
                    }

                    val contentColor = LocalContentColor.current
                    Canvas(
                        modifier = Modifier
                            .padding(
                                top = 16.dp,
                                end = 64.dp + 2.dp,
                                bottom = 32.dp + 16.dp
                            )
                            .fillMaxSize()
                    ) {
                        for (index in 0 .. 10) {
                            if (index != 0 && index != 10) {
                                drawLine(
                                    color = contentColor,
                                    start = Offset(x = size.width * index / 10f, y = 0f),
                                    end = Offset(x = size.width * index / 10f, y = size.height),
                                    strokeWidth = 2.dp.toPx(),
                                    cap = StrokeCap.Round,
                                    alpha = 0.2f
                                )
                            }
                            drawLine(
                                color = contentColor,
                                start = Offset(x = 0f, y = size.height * index / 10f),
                                end = Offset(x = size.width, y = size.height * index / 10f),
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round,
                                alpha = 0.2f
                            )
                        }
                        drawLine(
                            color =
                                if (changeAmount > 0f) Color.Red
                                else if (changeAmount < 0f) Color.Blue
                                else Color.Gray,
                            start = Offset(x = 0f, y = size.height * getRate(stockData.history.last())),
                            end = Offset(x = size.width, y = size.height * getRate(stockData.history.last())),
                            strokeWidth = 2.dp.toPx(),
                            cap = StrokeCap.Round,
                            alpha = 0.6f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(end = 64.dp)
                            .width(2.dp)
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .background(LocalContentColor.current)
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 32.dp - 1.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .align(Alignment.BottomCenter)
                            .background(LocalContentColor.current)
                    )

                    Column(
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                bottom = 32.dp + 4.dp,
                                start = 4.dp,
                                end = 4.dp
                            )
                            .width(64.dp - 8.dp)
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (index in 10 downTo 0) {
                            Text(
                                text = (priceRange.start + index / 10f * (priceRange.endInclusive - priceRange.start)).toString(),
                                maxLines = 1
                            )
                        }
                    }

                    var textHeight by remember { mutableStateOf(0) }
                    Text(
                        text = stockData.history.last().toString(),
                        maxLines = 1,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                bottom = 32.dp + 4.dp,
                                start = 4.dp,
                                end = 4.dp
                            )
                            .width(64.dp - 8.dp)
                            .onSizeChanged { textHeight = it.height }
                            .align(Alignment.TopEnd)
                            .offset(
                                y = LocalDensity.current.run {
                                    (chartSize.height.toDp() - (32.dp + 4.dp)) * getRate(stockData.history.last()) - textHeight.toDp() * 0.5f
                                }
                            )
                            .background(
                                color =
                                if (changeAmount > 0f) Color.Red
                                else if (changeAmount < 0f) Color.Blue
                                else Color.Gray,
                                shape = RoundedCornerShape(percent = 10)
                            )
                    )

                    Text(
                        text = "04:32", // TODO
                        fontSize = LocalDensity.current.run { 16.dp.toSp() },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.BottomEnd)
                    )

                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = with (LocalDensity.current) { 30.dp.toSp() },
                        fontWeight = FontWeight.Bold,
                        color = LocalContentColor.current,
                        modifier = Modifier
                            .padding(
                                bottom = 32.dp + 16.dp,
                                start = 16.dp
                            )
                            .align(Alignment.BottomStart)
                    )


                }

            }
        }
    }
}