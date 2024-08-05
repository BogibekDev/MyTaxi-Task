package dev.bogibek.mytaxitask.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.bogibek.mytaxitask.presentation.ui.screen.HomeScreen
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.AppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            AppTheme(darkTheme = uiMode == Configuration.UI_MODE_NIGHT_YES) {
                HomeScreen()
            }
        }
    }
}
