package dev.bogibek.mytaxitask.ui.theme.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import dev.bogibek.mytaxitask.ui.theme.DarkColorScheme
import dev.bogibek.mytaxitask.ui.theme.LightColorScheme

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val appColorScheme = if (darkTheme) AppDarkColorsPalette else AppLightColorsPalette


    CompositionLocalProvider(
        LocalAppColorsPalette provides appColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

}

val MaterialTheme.appColors: AppColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColorsPalette.current