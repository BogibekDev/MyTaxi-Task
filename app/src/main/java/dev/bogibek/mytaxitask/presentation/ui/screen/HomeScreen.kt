package dev.bogibek.mytaxitask.presentation.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.appColors

@OptIn(MapboxExperimental::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapViewportState = MapViewportState().apply {
                setCameraOptions {
                    zoom(13.6)
                    center(Point.fromLngLat(60.60766446461897, 41.5608633141494))
                    pitch(0.0)
                    bearing(0.0)
                }
            }
        ) {

        }

        Column {

        }

    }
}

@Preview(showSystemUi = true, device = Devices.DEFAULT, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}