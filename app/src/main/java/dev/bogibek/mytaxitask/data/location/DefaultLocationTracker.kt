package dev.bogibek.mytaxitask.data.location

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import dev.bogibek.mytaxitask.domain.location.LocationTracker
import dev.bogibek.mytaxitask.utils.hasLocationPermissions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application
) : LocationTracker {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!app.hasLocationPermissions() || !isGpsEnabled) return null

        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) continuation.resume(result) else continuation.resume(null)
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    continuation.resume(it)
                }

                addOnFailureListener {
                    continuation.resume(null)
                }

                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }

    }
}