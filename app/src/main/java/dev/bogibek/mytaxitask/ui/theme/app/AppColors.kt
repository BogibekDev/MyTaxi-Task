package dev.bogibek.mytaxitask.ui.theme.app

import androidx.compose.ui.graphics.Color

/*
* TODO()
* add first variable to AppColorsPalette
* then add color to each theme(AppLightColorsPalette and AppDarkColorsPalette)
*/


// General colors
val MainColor = Color(0xFF0062EA)


// Light Theme colors
val AppLightColorsPalette = AppColorsPalette(
    mainColor = MainColor,
//    mainBgColor = Color(0xFFF7F5F8),
    mainBgColor = Color.Red,
)


// Dark Theme colors
val AppDarkColorsPalette = AppColorsPalette(
    mainColor = MainColor,
//    mainBgColor = Color(0xFF282828),
    mainBgColor = Color.Blue,
)


