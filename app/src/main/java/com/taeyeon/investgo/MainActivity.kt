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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.model.Screen
import com.taeyeon.investgo.theme.InvestGoTheme
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