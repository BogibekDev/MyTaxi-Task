package dev.bogibek.mytaxitask.presentation.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.bogibek.mytaxitask.presentation.ui.screen.HomeScreen
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.AppTheme
import dev.bogibek.mytaxitask.utils.launchService
import dev.bogibek.mytaxitask.utils.mapboxAccessToken


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.launchService()
        MapboxOptions.accessToken = mapboxAccessToken
        setContent {
            val uiMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            AppTheme(darkTheme = uiMode == Configuration.UI_MODE_NIGHT_YES) {
                HomeScreen()
            }
        }
    }
}
