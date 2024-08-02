package dev.bogibek.mytaxitask.ui.theme.app

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColorsPalette(
    val mainBgColor: Color = Color.Unspecified,
    val mainColor: Color = Color.Unspecified,

)

val LocalAppColorsPalette = staticCompositionLocalOf { AppColorsPalette() }