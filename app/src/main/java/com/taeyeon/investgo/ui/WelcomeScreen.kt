package com.taeyeon.investgo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.taeyeon.investgo.R
import com.taeyeon.investgo.model.MainViewModel

@Composable
fun WelcomeScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
    ) {
        val (title, subTitle, closeButton) = createRefs()

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = with (LocalDensity.current) { 80.dp.toSp() },
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier
                .constrainAs(title) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, margin = 32.dp)
                }
        )

        Text(
            text = stringResource(id = R.string.app_explanation),
            fontSize = with (LocalDensity.current) { 20.dp.toSp() },
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier
                .constrainAs(subTitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start, margin = 32.dp)
                }
        )



    }
}