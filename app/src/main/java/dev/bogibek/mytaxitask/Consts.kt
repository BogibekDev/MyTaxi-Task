package dev.bogibek.mytaxitask

import android.Manifest

const val mapboxAccessToken = "pk.eyJ1Ijoib2dhYmVrZGV2IiwiYSI6ImNsc2JmbDNxZDAxZG4yanRkdXJkeHprZngifQ.weQa5cfF1xluCpdTreB1WQ"

val mustPermissions = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
)
val zoomDefault = 12.0