package dev.bogibek.mytaxitask.service


import android.annotation.SuppressLint
import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import dev.bogibek.mytaxitask.R
import dev.bogibek.mytaxitask.domain.entities.Location
import dev.bogibek.mytaxitask.domain.use_case.AddNewLocation
import dev.bogibek.mytaxitask.utils.hasLocationPermissions
import dev.bogibek.mytaxitask.utils.hasNotificationPermission
import dev.bogibek.mytaxitask.utils.interval
import dev.bogibek.mytaxitask.utils.minInterval
import dev.bogibek.mytaxitask.utils.notificationChannelId
import dev.bogibek.mytaxitask.utils.notificationChannelName
import dev.bogibek.mytaxitask.utils.notificationId
import dev.bogibek.mytaxitask.utils.notificationTitle
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : LifecycleService() {
    @Inject
    lateinit var app: Application

    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var addNewLocation: AddNewLocation

    lateinit var newLocation: Location

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            val lastLocation = result.lastLocation
            lastLocation?.let {
                newLocation = Location(longitude = it.longitude, latitude = it.latitude)
                lifecycleScope.launch {
                    addNewLocation(newLocation)
                }
            }
        }
    }


    override fun onCreate() {
        super.onCreate()
        if (app.hasNotificationPermission()) {
            createNotificationChannel()
            startForeground(notificationId, createNotification())
        }
        setupLocationTracking()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.removeLocationUpdates(locationCallBack)
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationTracking() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, interval
        ).apply {
            setMinUpdateIntervalMillis(minInterval)
            setWaitForAccurateLocation(false)
        }.build()

        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!app.hasLocationPermissions() || !isGpsEnabled) return

        locationClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.getMainLooper()
        )
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle(notificationTitle)
            .setContentText("Latitude: ${newLocation.latitude}\nLongitude: ${newLocation.longitude}")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()
    }

}