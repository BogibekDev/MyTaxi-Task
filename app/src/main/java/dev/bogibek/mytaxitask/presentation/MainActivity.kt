package dev.bogibek.mytaxitask.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.mapbox.common.MapboxOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.bogibek.mytaxitask.mapboxAccessToken
import dev.bogibek.mytaxitask.mustPermissions
import dev.bogibek.mytaxitask.presentation.ui.screen.HomeScreen
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.AppTheme
import dev.bogibek.mytaxitask.utils.hasPermissions


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        MapboxOptions.accessToken = mapboxAccessToken
        setContent {
            val uiMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            AppTheme(darkTheme = uiMode == Configuration.UI_MODE_NIGHT_YES) {
                if (hasPermissions()) {
                    HomeScreen()
                } else {
                    launchPermissionRequest()
                }
            }
        }
    }

    private fun launchPermissionRequest() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {}
        permissionLauncher.launch(mustPermissions.toTypedArray())
    }
}
