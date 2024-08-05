package dev.bogibek.mytaxitask.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dev.bogibek.mytaxitask.mustPermissions

fun Context.hasPermissions(): Boolean {
    return hasPermission(mustPermissions[0]) && hasPermission(mustPermissions[1])
}

fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

