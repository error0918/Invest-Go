package com.taeyeon.investgo.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.taeyeon.investgo.R

val gmarketSans = FontFamily(
    Font(R.font.gmarketsans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.gmarketsans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.gmarketsans_light, FontWeight.Light, FontStyle.Normal)
)

val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = gmarketSans
    ),
    titleMedium = TextStyle(
        fontFamily = gmarketSans
    ),
    titleLarge = TextStyle(
        fontFamily = gmarketSans
    )
)