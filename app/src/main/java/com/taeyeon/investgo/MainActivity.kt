@file:OptIn(ExperimentalAnimationApi::class)
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.taeyeon.investgo

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.taeyeon.investgo.data.Screen
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.theme.InvestGoTheme
import com.taeyeon.investgo.theme.gmarketSans
import com.taeyeon.investgo.ui.GameScreen
import com.taeyeon.investgo.ui.ReadyForGameScreen
import com.taeyeon.investgo.ui.WelcomeScreen

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
                    Test()
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
fun Test() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        var refreshTime by rememberSaveable { mutableStateOf(0.1f) }

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
                            text = "0.1초",
                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                            fontFamily = gmarketSans
                        )
                        Switch(
                            checked = refreshTime == 1f,
                            onCheckedChange = {
                                refreshTime = if (refreshTime == 0.1f) 1f else 0.1f
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
                            text = "1초",
                            fontSize = LocalDensity.current.run { 24.dp.toSp() },
                            fontFamily = gmarketSans
                        )
                    }

                    Text(
                        text = "달러 ($)", // TODO
                        fontSize = LocalDensity.current.run { 32.dp.toSp() },
                        fontWeight = FontWeight.Bold,
                        fontFamily = gmarketSans,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                    Text(
                        text = "04:32", // TODO
                        fontSize = LocalDensity.current.run { 24.dp.toSp() },
                        fontFamily = gmarketSans,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    )
                    
                }


                Surface(
                    modifier = Modifier.fillMaxWidth().height(2.dp),
                    color = LocalContentColor.current.copy(alpha = 0.5f),
                    shape = CircleShape,
                    content = {  }
                )


                //

                
            }
        }
    }
}