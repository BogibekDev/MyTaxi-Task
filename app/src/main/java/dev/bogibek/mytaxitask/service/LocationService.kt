package dev.bogibek.mytaxitask.service


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
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
import dev.bogibek.mytaxitask.utils.interval
import dev.bogibek.mytaxitask.utils.notificationChannelId
import dev.bogibek.mytaxitask.utils.notificationChannelName
import dev.bogibek.mytaxitask.utils.notificationId
import dev.bogibek.mytaxitask.utils.notificationTitle
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : LifecycleService() {

    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var addNewLocation: AddNewLocation

    var newLocation: Location? = null
    private lateinit var locationCallBack: LocationCallback

    private lateinit var manager: NotificationManager


    override fun onCreate() {
        super.onCreate()
        Log.d("Service", "onCreate: ")
        manager = getSystemService(NotificationManager::class.java) as NotificationManager
        setupLocationTracking()
        createNotificationChannel()
        startForeground(notificationId, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.removeLocationUpdates(locationCallBack)
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationTracking() {

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                Log.d("Service", "onLocationResult:${result.lastLocation} ")
                val lastLocation = result.lastLocation
                lastLocation?.let {
                    newLocation = Location(longitude = it.longitude, latitude = it.latitude)
                    Log.d("Service", "onLocationResult: $newLocation")
                    lifecycleScope.launch {
                        addNewLocation(newLocation!!)
                        updateNotification()
                    }
                }
            }
        }

        val locationRequest = LocationRequest
            .Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
            //.setMinUpdateDistanceMeters(minUpdateDistanceMeters)
            .build()


        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (this.hasLocationPermissions() || isGpsEnabled) {
            locationClient.requestLocationUpdates(
                locationRequest, locationCallBack, Looper.getMainLooper()
            )
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle(notificationTitle)
            .setContentText(if (newLocation != null) "Latitude: ${newLocation?.latitude}\nLongitude: ${newLocation?.longitude}" else "Loading ...")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun updateNotification() {
        val updatedNotification = createNotification()
        manager.notify(notificationId, updatedNotification)
    }

}