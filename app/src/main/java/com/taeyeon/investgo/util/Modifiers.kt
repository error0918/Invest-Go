package com.taeyeon.investgo.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlin.math.cos
import kotlin.math.sin

fun Modifier.spinningGradientBackground(
    colors: List<Color> = listOf(Color.Red, Color.Blue),
    shape: Shape = RectangleShape
): Modifier = composed {
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

    this
        .onSizeChanged { size = it }
        .background(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(
                    x = size.width / 2 + biggerSection * sin(angle) / 2,
                    y = size.height / 2 + biggerSection * cos(angle) / 2
                ),
                end = Offset(
                    x = size.width / 2 - biggerSection * sin(angle) / 2,
                    y = size.height / 2 - biggerSection * cos(angle) / 2
                )
            ),
            shape = shape
        )
}