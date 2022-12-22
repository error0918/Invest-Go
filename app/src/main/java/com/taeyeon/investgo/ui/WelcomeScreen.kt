package com.taeyeon.investgo.ui

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.taeyeon.investgo.util.spinningGradientBackground
import kotlin.random.Random
import kotlin.system.exitProcess

@Composable
fun WelcomeScreen(
    mainViewModel: MainViewModel = MainViewModel(LocalContext.current)
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        mainViewModel.welcomeViewModel.userName = getRandomName()
        mainViewModel.welcomeViewModel.userNameErrorMessage = checkUserNameError(context, mainViewModel.welcomeViewModel.userName)
    }

    val contentColor = MaterialTheme.colorScheme.onPrimary
        .copy(alpha =  1f / 2f).compositeOver(
            MaterialTheme.colorScheme.onSecondary
                .copy(alpha =  2f / 3f).compositeOver(
                    MaterialTheme.colorScheme.onTertiary
                )
        )
    val errorColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        .compositeOver(contentColor)

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
        val (title, subTitle, controlColumn, closeButton) = createRefs()

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontSize = with (LocalDensity.current) { 80.dp.toSp() },
            fontWeight = FontWeight.Bold,
            color = contentColor,
            modifier = Modifier
                .constrainAs(title) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
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
                    start.linkTo(parent.start)
                    width = Dimension.percent(0.4f)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(controlColumn) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end)
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
                        color =
                        if (mainViewModel.welcomeViewModel.userNameErrorMessage == null) contentColor
                        else errorColor,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(
                        vertical = 16.dp,
                        horizontal = 32.dp
                    )
            ) {
                var iconSize by remember { mutableStateOf(IntSize.Zero) }

                BasicTextField(
                    value = mainViewModel.welcomeViewModel.userName,
                    onValueChange = {
                        mainViewModel.welcomeViewModel.userName = it
                        mainViewModel.welcomeViewModel.userNameErrorMessage = checkUserNameError(context, mainViewModel.welcomeViewModel.userName)
                    },
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
                    onClick = {
                        mainViewModel.welcomeViewModel.userName = getRandomName()
                        mainViewModel.welcomeViewModel.userNameErrorMessage = checkUserNameError(context, mainViewModel.welcomeViewModel.userName)
                    },
                    modifier = Modifier
                        .width(LocalDensity.current.run { iconSize.height.toDp() })
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                        .onSizeChanged { iconSize = it }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = stringResource(id = R.string.welcome_name_random),
                        tint = contentColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }

                Text(
                    text = stringResource(id = R.string.welcome_nickname),
                    style = MaterialTheme.typography.labelSmall,
                    color =
                    if (mainViewModel.welcomeViewModel.userNameErrorMessage == null) contentColor
                    else errorColor,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                )

                mainViewModel.welcomeViewModel.userNameErrorMessage?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall,
                        color = errorColor,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }

            }

            Button(
                onClick = { mainViewModel.navHostController.navigate(Screen.ReadyForGame.name) },
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(
                    width = 4.dp,
                    color =
                        if (mainViewModel.welcomeViewModel.userNameErrorMessage == null) contentColor
                        else errorColor
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray.copy(alpha = 0.6f).compositeOver(MaterialTheme.colorScheme.primary),
                    contentColor = contentColor,
                    disabledContainerColor = Color.DarkGray.copy(alpha = 0.6f).compositeOver(MaterialTheme.colorScheme.primary).copy(alpha = 0.7f).compositeOver(Color.LightGray),
                    disabledContentColor = contentColor.copy(alpha = 0.7f).compositeOver(Color.LightGray)
                ),
                enabled = mainViewModel.welcomeViewModel.userNameErrorMessage == null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_play),
                    fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { /*TODO*/ },
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
                    text = stringResource(id = R.string.welcome_more),
                    fontSize = with(LocalDensity.current) { 40.dp.toSp() },
                    fontWeight = FontWeight.Bold
                )
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
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
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

fun getDigitNumber(number: Int, digits: Int): String {
    return if (digits > 0) {
        if (number.toString().length >= digits) {
            number.toString().substring(0, digits)
        } else {
            "0".repeat(digits - number.toString().length) + number
        }
    } else ""
}

fun getRandomName(): String {
    val animalList = listOf("가면팜사향고양이", "가시두더지", "가젤영양", "가지뿔영양", "갈기늑대", "갈기쥐", "갈라파고스물개", "갈라파고스펭귄", "갈색여우원숭이", "강멧돼지", "개", "개미핥기", "개코원숭이", "거농원숭이", "거미원숭이", "검둥원숭이", "검은고함원숭이", "검은머리꼬리감기원숭이", "검은수염고래", "검은여우원숭이", "게잡이물범", "게잡이원숭이", "고라니", "고래", "고릴라", "고슴도치", "고양이", "고함원숭이", "곰", "관여우원숭이", "관박쥐", "귀신고래 ·그랜트가젤", "그랜트얼룩말", "그레비얼룩말", "그레이하운드", "그리슨족제비", "금강산코박쥐", "기린", "긴귀주머니오소리", "긴칼뿔오릭스", "긴팔원숭이", "꼬리감기원숭이", "꿀먹이박쥐", "고모도왕도마뱀", "나무늘보", "낙타", "날다람쥐", "날쥐", "남극물개", "남방바다사자", "남방큰돌고래", "낫돌고래", "너구리", "네발가락고슴도치", "네뿔영양", "노란눈썹펭귄", "노란머리큰박쥐", "노루", "눈토끼", "뉴질랜드물개", "느림보늘보원숭이", "늘보곰", "늘보원숭이", "늘보주머니쥐", "늪영양", "늑대", "다람쥐", "다람쥐원숭이", "다마가젤", "다이커영양", "단봉낙타", "담비", "당나귀", "대륙담비", "덤불멧돼지", "도르카스가젤", "동부회색청서", "돌고래", "돌산양", "돼지", "돼지사슴", "두더지", "두크마른원숭이", "뒤쥐", "등줄쥐", "딩고", "땃쥐", "뚱뚱꼬리저빌", "라마", "로키산양", "마른원숭이", "마모셋원숭이", "마카크원숭이", "마코르염소", "말", "망토개코원숭이", "멧돼지", "멧토끼", "몽구스여우원숭이", "물개", "물영양", "미슈미타킨", "범고래", "바다사자", "바다표범", "바르바리양", "바르바리마카크", "바비루사", "바위너구리", "박쥐", "반달가슴곰", "버마고양이", "범고래", "베르베트원숭이", "벵골호랑이", "봉고", "부탄타킨", "북극곰", "북극여우", "불곰", "불독", "불테리어", "붉은고함원숭이", "붉은목도리여우원숭이", "붉은여우", "붉은박쥐", "비단원숭이", "비버", "비쿠냐", "사막여우", "사슴", "사자", "사자꼬리마카크원숭이", "사향고양이", "사향노루", "산양", "산토끼", "삵", "상괭이", "생쥐", "샴고양이", "셰퍼드", "소", "솜머리비단원숭이", "수달", "수염고래", "스라소니", "스컹크", "시베리아호랑이", "시베리안 허스키", "시파카원숭이", "쓰촨타킨", "아메리카너구리", "아메리카들소", "아메리카불곰", "아이벡스", "아프리카물소", "안경곰", "안경원숭이", "알파카", "알락꼬리여우원숭이", "알래스카불곰", "양", "양털원숭이", "에스키모개", "여우", "여우청서", "여우원숭이", "염소", "영양", "오랑우탄", "오소리", "왕치타", "올빼미원숭이", "왈라비", "원숭이", "유대하늘다람쥐", "유럽들소", "인도들소", "인도별사슴", "인도영양", "일본원숭이", "일본산양", "작은쥐여우원숭이", "재규어", "재칼", "저빌", "점박이물범", "족제비", "주머니고양이", "주머니쥐", "줄무늬스컹크", "쥐", "쥐여우원숭이", "쥐캥거루", "진도개", "참고래", "참돌고래", "청설모", "치와와", "치타", "친칠라", "친칠라생쥐", "침팬지", "캘리포니아다람쥐", "캥거루", "코끼리", "코뿔소", "코알라", "코요테", "코주부원숭이", "콜로부스원숭이", "콜리", "콧수염게논", "콰가", "큰개미핥기", "큰고래", "큰뿔양", "타킨", "테리어", "토끼", "토끼박쥐", "톰슨가젤", "티베트산양", "파타스원숭이", "팬더마우스", "페르시아고양이· 펭귄· 표범", "푸들", "퓨마", "피그미고슴도치", "하늘다람쥐", "하마", "향고래· 한국늑대", "한국표범", "향유고래", "호랑이", "호저", "황금타킨", "혹등고래", "흰목꼬리감기원숭이", "흰목도리여우원숭이", "흰수염고래", "히말라야산양")
    return "${animalList.random()} ${getDigitNumber(Random.nextInt(0, 1000), 3)}"
}

fun checkUserNameError(context: Context, userName: String): String? {
    return when {
        userName.isBlank() -> context.getString(R.string.welcome_user_name_error_blank)
        userName.length > 15 -> context.getString(R.string.welcome_user_name_error_too_long)
        else -> null
    }
}