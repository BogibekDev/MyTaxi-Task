package dev.bogibek.mytaxitask.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import dev.bogibek.mytaxitask.service.LocationService

fun Context.hasLocationPermissions(): Boolean {
    return hasPermission(mustPermissions[0]) && hasPermission(mustPermissions[1])
}


fun Context.hasNotificationPermission(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
    return hasPermission(mustPermissions[2])
}

fun Context.hasBackgroundLocationPermission(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return true
    return hasPermission(mustPermissions[3])
}

fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}


fun Context.launchService() {
    if (hasNotificationPermission()) {
        val intent = Intent(this, LocationService::class.java)
        ContextCompat.startForegroundService(this, intent)
    }
}


