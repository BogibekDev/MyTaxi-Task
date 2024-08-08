package dev.bogibek.mytaxitask.utils

import android.Manifest

const val mapboxAccessToken = "pk.eyJ1Ijoib2dhYmVrZGV2IiwiYSI6ImNsc2JmbDNxZDAxZG4yanRkdXJkeHprZngifQ.weQa5cfF1xluCpdTreB1WQ"

val mustPermissions = listOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.POST_NOTIFICATIONS,
)
const val zoomDefault = 12.0

const val notificationChannelId = "LocationServiceChannel"
const val notificationId = 4432

const val interval = 15 * 60 * 1000L
const val minInterval = 5 * 60 * 1000L
const val notificationChannelName = "Location Service Channel"
const val notificationTitle = "Tracking new location"