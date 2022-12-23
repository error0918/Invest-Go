package com.taeyeon.investgo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.taeyeon.investgo.model.GameViewModel
import com.taeyeon.investgo.model.MainViewModel

@Composable
fun GameScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    LaunchedEffect(mainViewModel.welcomeViewModel.userName) {
        mainViewModel.gameViewModel.stopTimer()
        mainViewModel.gameViewModel = GameViewModel(
            name = mainViewModel.welcomeViewModel.userName.trim(),
            time = mainViewModel.readyForGameViewModel.timeList[mainViewModel.readyForGameViewModel.selected].first
        )
    }

    if (mainViewModel.gameViewModel.isMenuPopupShowing) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { mainViewModel.gameViewModel.isMenuPopupShowing = false },
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                excludeFromSystemGesture = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Gray.copy(alpha = 0.2f)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                }
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray
            )
            .padding(32.dp)
    ) {
        val (menuIconButton, timerText, scoreText1, scoreText2) = createRefs()

        IconButton(
            onClick = { mainViewModel.gameViewModel.isMenuPopupShowing = !mainViewModel.gameViewModel.isMenuPopupShowing },
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
}