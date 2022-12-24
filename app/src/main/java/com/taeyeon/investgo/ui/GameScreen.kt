package com.taeyeon.investgo.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.taeyeon.investgo.R
import com.taeyeon.investgo.model.GameViewModel
import com.taeyeon.investgo.model.MainViewModel

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

        ConstraintLayout(
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
                .padding(32.dp)
        ) {
            val (menuIconButton, timerText, scoreText1, scoreText2) = createRefs()

            IconButton(
                onClick = {
                    mainViewModel.gameViewModel.isShowingMenu =
                        !mainViewModel.gameViewModel.isShowingMenu
                },
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(menuIconButton) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .border(
                        width = 2.dp,
                        color = LocalContentColor.current,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "TODO"
                )
            }

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
                    .constrainAs(timerText) {
                        top.linkTo(menuIconButton.top)
                        bottom.linkTo(menuIconButton.bottom)
                        start.linkTo(menuIconButton.end, margin = 32.dp)
                    }
            )

            Text(
                text = "⌈${mainViewModel.gameViewModel.name}⌋님의 자산: ",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                fontSize = LocalDensity.current.run { 32.dp.toSp() },
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .constrainAs(scoreText1) {
                        centerVerticallyTo(scoreText2)
                        start.linkTo(timerText.end, margin = 32.dp)
                        end.linkTo(scoreText2.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = "${mainViewModel.gameViewModel.score}원",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = LocalDensity.current.run { 32.dp.toSp() },
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .constrainAs(scoreText2) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )

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