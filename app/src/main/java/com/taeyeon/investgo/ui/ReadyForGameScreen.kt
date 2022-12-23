package com.taeyeon.investgo.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowLeft
import androidx.compose.material.icons.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.taeyeon.investgo.R
import com.taeyeon.investgo.data.Screen
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.util.spinningGradientBackground

@Composable
fun ReadyForGameScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    val contentColor = MaterialTheme.colorScheme.onPrimary
        .copy(alpha =  1f / 2f).compositeOver(
            MaterialTheme.colorScheme.onSecondary
                .copy(alpha =  2f / 3f).compositeOver(
                    MaterialTheme.colorScheme.onTertiary
                )
        )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .spinningGradientBackground(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary
                )
            )
            .padding(32.dp)
    ) {
        val (timeText, timeColumn, explanationText, previousButton, nextButton) = createRefs()

        Text(
            text = stringResource(id = R.string.ready_for_game_select_time),
            style = MaterialTheme.typography.titleLarge,
            fontSize = with (LocalDensity.current) { 30.dp.toSp() },
            fontWeight = FontWeight.Normal,
            color = contentColor,
            modifier = Modifier
                .constrainAs(timeText) {
                    start.linkTo(parent.start)
                    bottom.linkTo(timeColumn.top, margin = 16.dp)
                    width = Dimension.percent(0.6f)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(timeColumn) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                    height = Dimension.percent(0.6f)
                    width = Dimension.percent(0.6f)
                },
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            mainViewModel.readyForGameViewModel.timeList.forEachIndexed { index, item ->
                val hapticFeedback = LocalHapticFeedback.current
                Button(
                    onClick = {
                        mainViewModel.readyForGameViewModel.selected = index
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    },
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(
                        width = 4.dp,
                        color = contentColor
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.6f).let {
                            if (mainViewModel.readyForGameViewModel.selected == index) it.compositeOver(MaterialTheme.colorScheme.primary)
                            else it
                        },
                        contentColor = contentColor,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        if (mainViewModel.readyForGameViewModel.selected == index) {
                            var iconSize by remember { mutableStateOf(IntSize.Zero) }

                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = stringResource(id = R.string.ready_for_game_selected),
                                tint = contentColor,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .onSizeChanged { iconSize = it }
                                    .size(LocalDensity.current.run { iconSize.height.toDp() })
                                    .align(Alignment.CenterEnd)
                                    .padding(16.dp)
                            )
                        }

                        Text(
                            text = item.second,
                            fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )

                    }
                }
            }
        }

        Text(
            text = stringResource(id = R.string.ready_for_game_explanation),
            style = MaterialTheme.typography.titleLarge,
            fontSize = with (LocalDensity.current) { 30.dp.toSp() },
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.End,
            color = contentColor,
            modifier = Modifier
                .constrainAs(explanationText) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.35f)
                }
        )

        OutlinedButton(
            onClick = { mainViewModel.navHostController.navigateUp() },
            shape = CircleShape,
            border = BorderStroke(
                width = 4.dp,
                color = contentColor
            ),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(previousButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowLeft,
                        contentDescription = stringResource(id = R.string.ready_for_game_previous)
                    )
                    Text(text = stringResource(id = R.string.ready_for_game_previous))
                }
            }
        }

        OutlinedButton(
            onClick = { mainViewModel.navHostController.navigate(route = Screen.Game.name) },
            shape = CircleShape,
            border = BorderStroke(
                width = 4.dp,
                color = contentColor
            ),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(nextButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowRight,
                        contentDescription = stringResource(id = R.string.ready_for_game_play)
                    )
                    Text(text = stringResource(id = R.string.ready_for_game_play))
                }
            }
        }

    }
}