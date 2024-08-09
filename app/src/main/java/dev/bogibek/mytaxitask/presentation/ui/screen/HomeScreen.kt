package dev.bogibek.mytaxitask.presentation.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import dev.bogibek.mytaxitask.R
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.appColors
import dev.bogibek.mytaxitask.presentation.ui.util.Animation
import dev.bogibek.mytaxitask.presentation.ui.view.BottomSheetView
import dev.bogibek.mytaxitask.presentation.ui.view.PermissionNeedScreen
import dev.bogibek.mytaxitask.presentation.ui.view.ToggleButton
import dev.bogibek.mytaxitask.presentation.viewmodel.HomeViewModel
import dev.bogibek.mytaxitask.utils.hasLocationPermissions
import dev.bogibek.mytaxitask.utils.hasNotificationPermission
import dev.bogibek.mytaxitask.utils.launchService
import dev.bogibek.mytaxitask.utils.mustPermissions
import dev.bogibek.mytaxitask.utils.zoomDefault

@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    val isDarkTheme = isSystemInDarkTheme()
    val mapStyle = remember(isDarkTheme) {
        if (isDarkTheme) Style.DARK else Style.MAPBOX_STREETS
    }
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val hasPermission = remember {
        mutableStateOf(context.hasLocationPermissions())
    }

    val isFirstTime = remember {
        mutableStateOf(true)
    }
    val iconVisible = rememberSaveable {
        mutableStateOf(true)
    }
    val bottomSheetState =
        rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()

    val zoom = remember {
        mutableStateOf(zoomDefault)
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(zoom.value)
            state.location?.let {
                center(Point.fromLngLat(it.longitude, it.latitude))
            }
        }
    }

    LaunchedEffect(zoom.value) {
        mapViewportState.flyTo(
            cameraOptions = cameraOptions {
                zoom(zoom.value)
            },
            animationOptions = MapAnimationOptions.mapAnimationOptions {
                duration(500L)
            }
        )
    }

    LaunchedEffect(mapViewportState.cameraState) {
        bottomSheetState.partialExpand()
    }

    LaunchedEffect(bottomSheetState.currentValue) {
        iconVisible.value = bottomSheetState.currentValue != SheetValue.Expanded
    }

    LaunchedEffect(state.location) {
        if (isFirstTime.value && state.location != null) {
            isFirstTime.value = false
            mapViewportState.flyTo(
                cameraOptions = cameraOptions {
                    state.location?.let {
                        center(Point.fromLngLat(it.longitude, it.latitude))
                    }
                    zoom(zoom.value)
                },
                animationOptions = MapAnimationOptions.mapAnimationOptions {
                    duration(2000L)
                }
            )
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (context.hasLocationPermissions() && context.hasNotificationPermission()) {
            hasPermission.value = true
            context.launchService()
        }
    }



    if (hasPermission.value) {
        if (state.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    BottomSheetView()
                },
                sheetPeekHeight = 132.dp,
                sheetDragHandle = {}
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    MapboxMap(
                        modifier = Modifier.fillMaxSize(),
                        mapViewportState = mapViewportState,
                        style = {
                            MapStyle(style = mapStyle)
                        },
                        scaleBar = {}
                    ) {
                        state.location?.let {
                            ViewAnnotation(
                                options = viewAnnotationOptions {
                                    geometry(Point.fromLngLat(it.longitude, it.latitude))
                                    allowOverlap(true)
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_car),
                                    contentDescription = "car"
                                )
                            }
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.appColors.mainBgColor)
                                        .clickable {

                                        }
                                        .padding(16.dp),
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.appColors.mainTextColor
                                )

                                ToggleButton(
                                    modifier = Modifier.weight(1f),
                                    onDetect = {
                                        if (it) iconVisible.value = !iconVisible.value
                                    }
                                )

                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.appColors.mainBgColor)
                                        .clickable {},
                                    contentAlignment = Alignment.Center
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(4.dp)
                                            .background(
                                                MaterialTheme.appColors.mainColor,
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "95",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                fontFamily = FontFamily(Font(R.font.lato_bold)),
                                                color = Color(0xFF121212),
                                                textAlign = TextAlign.Center,
                                            )
                                        )
                                    }


                                }

                            }


                        }
                        Spacer(modifier = Modifier.height(56.dp))

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Animation(direction = 1, visible = iconVisible.value) {

                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(MaterialTheme.appColors.mainBgColor)
                                    .clickable {}
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.appColors.leftIconBack)
                                        .padding(12.dp),
                                    painter = painterResource(id = R.drawable.ic_inner),
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.appColors.leftIconTint
                                )

                            }
                        }

                        Animation(direction = -1, visible = iconVisible.value) {

                            Column(
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    iconVisible.value = false
                                },
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.appColors.mainBgColor)
                                        .clickable {
                                            if (zoom.value < 18.0) {
                                                zoom.value++
                                            }
                                        }
                                        .padding(20.dp),
                                    painter = painterResource(id = R.drawable.ic_add),
                                    contentDescription = "add",
                                    tint = MaterialTheme.appColors.leftIconTint
                                )

                                Icon(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.appColors.mainBgColor)
                                        .clickable {
                                            if (zoom.value > 0.0) {
                                                zoom.value--
                                            }
                                        }
                                        .padding(16.dp),
                                    painter = painterResource(id = R.drawable.ic_minus),
                                    contentDescription = "minus",
                                    tint = MaterialTheme.appColors.leftIconTint
                                )

                                Icon(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(MaterialTheme.appColors.mainBgColor)
                                        .clickable {
                                            if (context.hasLocationPermissions()) {
                                                mapViewportState.flyTo(
                                                    cameraOptions = cameraOptions {
                                                        state.location?.let {
                                                            center(
                                                                Point.fromLngLat(
                                                                    it.longitude,
                                                                    it.latitude
                                                                )
                                                            )
                                                        }
                                                        zoom(zoomDefault)
                                                    },

                                                    animationOptions = MapAnimationOptions.mapAnimationOptions {
                                                        duration(2000L)
                                                    }
                                                )
                                            }
                                        }
                                        .padding(16.dp),
                                    painter = painterResource(id = R.drawable.ic_send),
                                    contentDescription = "location",
                                    tint = MaterialTheme.appColors.locationColor
                                )

                            }

                        }
                    }

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