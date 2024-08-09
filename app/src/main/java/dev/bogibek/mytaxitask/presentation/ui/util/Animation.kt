package dev.bogibek.mytaxitask.presentation.ui.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Animation(
    modifier: Modifier = Modifier,
    direction: Int,
    visible: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { direction * it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { direction * it }) + fadeOut()
    ) {
        content()
    }


}