package dev.bogibek.mytaxitask.presentation.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogibek.mytaxitask.R
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.appColors

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    onDetect: (Boolean) -> Unit,
) {

    var busyButtonVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var activeButtonVisible by rememberSaveable {
        mutableStateOf(true)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.appColors.mainBgColor)
            .padding(4.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (busyButtonVisible) onDetect(true)
                    busyButtonVisible = true
                    activeButtonVisible = false
                },
            contentAlignment = Alignment.Center
        ) {

            BtnBackground(
                direction = 1,
                activeButtonVisible = busyButtonVisible,
                background = MaterialTheme.appColors.busyButtonBackground
            )

            Text(
                text = "Band",
                style = TextStyle(
                    color = if (busyButtonVisible) Color.White else MaterialTheme.appColors.busyBtnTextColor,
                    fontSize = 18.sp,
                    fontFamily = if (busyButtonVisible) FontFamily(Font(R.font.lato_bold)) else FontFamily(
                        Font(R.font.lato_regular)
                    ),
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (activeButtonVisible) onDetect(true)
                    activeButtonVisible = true
                    busyButtonVisible = false
                },
            contentAlignment = Alignment.Center
        ) {

            BtnBackground(
                direction = -1,
                activeButtonVisible = activeButtonVisible,
                background = MaterialTheme.appColors.mainColor
            )

            Text(
                text = "Faol",
                style = TextStyle(
                    color = if (activeButtonVisible) MaterialTheme.appColors.activeBtnTextColor else MaterialTheme.appColors.mainTextColor,
                    fontSize = 18.sp,
                    fontFamily = if (activeButtonVisible) FontFamily(Font(R.font.lato_bold)) else FontFamily(
                        Font(R.font.lato_regular)
                    ),
                )
            )
        }

    }

}

@Composable
fun BtnBackground(
    direction: Int,
    activeButtonVisible: Boolean,
    background: Color
) {

    AnimatedVisibility(
        visible = activeButtonVisible,
        enter = slideInHorizontally(initialOffsetX = { direction * it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { direction * it }) + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    background,
                    shape = RoundedCornerShape(10.dp)
                )
        )

    }

}