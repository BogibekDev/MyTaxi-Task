package dev.bogibek.mytaxitask.presentation.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.bogibek.mytaxitask.presentation.ui.screen.HomeScreen
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.AppTheme
import dev.bogibek.mytaxitask.service.LocationService
import dev.bogibek.mytaxitask.utils.mapboxAccessToken


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLocationService()
        enableEdgeToEdge()
        MapboxOptions.accessToken = mapboxAccessToken
        setContent {
            val uiMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            AppTheme(darkTheme = uiMode == Configuration.UI_MODE_NIGHT_YES) {
                HomeScreen()
            }
        }
    }

    private fun startLocationService() {
        val intent = Intent(this, LocationService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
}
