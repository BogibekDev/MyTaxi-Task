package dev.bogibek.mytaxitask.presentation.ui.theme.custom

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val appColorSchemePalette = if (darkTheme) AppDarkColorsPalette else AppLightColorsPalette

    CompositionLocalProvider(
        LocalAppColorsPalette provides appColorSchemePalette
    ) {
        MaterialTheme(content = content)
    }

}

val MaterialTheme.appColors: AppColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalAppColorsPalette.current