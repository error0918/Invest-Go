@file:OptIn(ExperimentalMaterial3Api::class)

package com.taeyeon.investgo.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.taeyeon.investgo.R
import com.taeyeon.investgo.data.Screen
import com.taeyeon.investgo.model.MainViewModel
import com.taeyeon.investgo.theme.gmarketSans
import kotlin.math.cos
import kotlin.math.sin
import kotlin.system.exitProcess

@Composable
fun WelcomeScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val biggerSection = if (size.width > size.height) size.width else size.height
    val angle by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = Math.toRadians(360.0).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size = it }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    ),
                    start = Offset(
                        x = size.width / 2 + biggerSection * sin(angle) / 2,
                        y = size.height / 2 + biggerSection * cos(angle) / 2
                    ),
                    end = Offset(
                        x = size.width / 2 - biggerSection * sin(angle) / 2,
                        y = size.height / 2 - biggerSection * cos(angle) / 2
                    )
                )
            )
    ) {
        val (title, subTitle, controlColumn, closeButton) = createRefs()

        val contentColor = MaterialTheme.colorScheme.onPrimary
            .copy(alpha =  1f / 3f).compositeOver(
                MaterialTheme.colorScheme.onSecondary
                    .copy(alpha =  2f / 3f).compositeOver(
                        MaterialTheme.colorScheme.onTertiary
                    )
            )

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontSize = with (LocalDensity.current) { 80.dp.toSp() },
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier
                .constrainAs(title) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = 32.dp)
                    width = Dimension.percent(0.4f)
                }
        )

        Text(
            text = stringResource(id = R.string.app_explanation),
            style = MaterialTheme.typography.titleLarge,
            fontSize = with (LocalDensity.current) { 16.dp.toSp() },
            fontWeight = FontWeight.Light,
            color = contentColor,
            modifier = Modifier
                .constrainAs(subTitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, margin = 32.dp)
                    width = Dimension.percent(0.4f)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(controlColumn) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, margin = 32.dp)
                    height = Dimension.percent(0.5f)
                    width = Dimension.percent(0.4f)
                },
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = 4.dp,
                        color = contentColor,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(
                        vertical = 16.dp,
                        horizontal = 32.dp
                    )
            ) {
                var iconSize by remember { mutableStateOf(IntSize.Zero) }

                BasicTextField(
                    value = "userName 000000000000000",
                    onValueChange = {},
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = gmarketSans,
                        fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    ),
                    cursorBrush = SolidColor(contentColor),
                    singleLine = true,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = LocalDensity.current.run { iconSize.width.toDp() })
                )

                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .width(LocalDensity.current.run { iconSize.height.toDp() })
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .onSizeChanged { iconSize = it }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }

                Text(
                    text = "fsdfljsdf;kjfsklj;alkjdfl;k",
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.6f).compositeOver(contentColor),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )

            }

            listOf(
                stringResource(id = R.string.welcome_play) to { mainViewModel.navHostController.navigate("${Screen.Game.name}/NAME") },
                stringResource(id = R.string.welcome_more) to { /* TODO */ }
            ).forEach {
                Button(
                    onClick = it.second,
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(
                        width = 4.dp,
                        color = contentColor
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.6f).compositeOver(MaterialTheme.colorScheme.primary),
                        contentColor = contentColor,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(
                        text = it.first,
                        fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        OutlinedButton(
            onClick = { exitProcess(0) },
            shape = CircleShape,
            border = BorderStroke(
                width = 4.dp,
                color = contentColor
            ),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .constrainAs(closeButton) {
                    top.linkTo(parent.top, margin = 32.dp)
                    end.linkTo(parent.end, margin = 32.dp)
                }
        ) {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.welcome_close)
                    )
                    Text(text = stringResource(id = R.string.welcome_close))
                }
            }
        }

    }
}