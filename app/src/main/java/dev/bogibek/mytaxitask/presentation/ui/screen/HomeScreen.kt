package dev.bogibek.mytaxitask.presentation.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import dev.bogibek.mytaxitask.mustPermissions
import dev.bogibek.mytaxitask.presentation.ui.view.PermissionNeedScreen
import dev.bogibek.mytaxitask.utils.hasPermissions
import dev.bogibek.mytaxitask.zoomDefault

@OptIn(MapboxExperimental::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val hasPermission = remember {
        mutableStateOf(context.hasPermissions())
    }
    val zoom = remember {
        mutableStateOf(zoomDefault)
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(zoom.value)
            center(Point.fromLngLat(60.60766446461897, 41.5608633141494))
        }
    }


    LaunchedEffect(zoom.value) {
        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                zoom(zoom.value)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions {
                duration(2000L)
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (context.hasPermissions()) {
            hasPermission.value = true
        }
    }

    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }


    if (hasPermission.value) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {

            MapboxMap(
                modifier = Modifier.fillMaxSize(),
                mapViewportState = mapViewportState,
                style = {
                    MapStyle(style = Style.MAPBOX_STREETS)
                }

            ) {
                ViewAnnotation(
                    options = viewAnnotationOptions {
                        geometry(Point.fromLngLat(60.60766446461897, 41.5608633141494))
                        allowOverlap(true)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        tint = Color.Yellow,
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = "Car",
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            ) {

                Box(modifier = Modifier
                    .clickable {
                        if (zoom.value < 18.0) {
                            zoom.value++
                        }
                    }
                    .background(Color.White)
                    .padding(all = 16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "zoom in")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .clickable {
                        if (zoom.value > 0.0) {
                            zoom.value--
                        }
                    }
                    .background(Color.White)
                    .padding(all = 16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Abc, contentDescription = "zoom out")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .clickable {
                        if (context.hasPermissions()) {
//                            getLocation()
                            zoom.value = zoomDefault
                        }
                    }
                    .background(Color.White)
                    .padding(all = 16.dp)
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location")
                }

            }

        }
    } else {
        PermissionNeedScreen(
            modifier.fillMaxSize(),
            onPermissionGranted = {
                permissionLauncher.launch(mustPermissions.toTypedArray())
            },
            onPermissionDenied = {
                Toast.makeText(
                    context,
                    "Permission bermasangiz dasturdan foydalana olmaysiz.",
                    Toast.LENGTH_SHORT
                ).show()
            },
        )
    }
}

@Preview(showSystemUi = true, device = Devices.DEFAULT, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}