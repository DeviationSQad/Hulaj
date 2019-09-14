package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.kamilszustak.hulapp.util.getSystemService
import timber.log.Timber

class TrackViewModel(application: Application) : BaseViewModel(application) {

    private val LOCATION_UPDATE_MIN_INTERVAL: Long = 3000
    private val LOCATION_UPDATE_MIN_DISTANCE: Float = 10.0F

    private val locationManager = application.applicationContext.getSystemService<LocationManager>()

    private val _trackLength = MutableLiveData<Double>(0.0)
    val trackLengthLiveData: LiveData<Double>
        get() = _trackLength

    private val _trackingState = MutableLiveData<TrackingState>(TrackingState.NOT_STARTED)
    val trackingState: LiveData<TrackingState>
        get() = _trackingState

    private val _isLocationPermissionGranted = MutableLiveData<Boolean>()
    val isLocationPermissionGranted: LiveData<Boolean>
        get() = _isLocationPermissionGranted

    private lateinit var previousLocation: Location
    private var isFirstLocationUpdate = true

    private val locationListener = object: LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (isFirstLocationUpdate) {
                location?.let {
                    previousLocation = it
                }
                isFirstLocationUpdate = false
            } else {
                val newDistance = previousLocation.distanceTo(location)
                val previousDistance = _trackLength.value
                previousDistance?.let {
                    _trackLength.value = it + newDistance
                }
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    init {
        _isLocationPermissionGranted.value = isLocationPermissionGranted(application)
    }

    private fun isLocationPermissionGranted(application: Application): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            application.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        return  permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun startTracking() {
        val currentTrackingState = _trackingState.value

        if (currentTrackingState == TrackingState.NOT_STARTED ||
            currentTrackingState == TrackingState.FINISHED) {

            requestLocationUpdates()
        }
    }

    fun pauseTracking() {
        val currentTrackingState = _trackingState.value

        if (currentTrackingState == TrackingState.STARTED) {
            locationManager.removeUpdates(locationListener)
        }
    }

    fun resumeTracking() {
        val currentTrackingState = _trackingState.value

        if (currentTrackingState == TrackingState.PAUSED) {
            requestLocationUpdates()
        }
    }

    fun stopTracking() {
        val currentTrackingState = _trackingState.value

        if (currentTrackingState == TrackingState.PAUSED) {
            locationManager.removeUpdates(locationListener)
        }
    }

    private fun requestLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_MIN_INTERVAL,
                LOCATION_UPDATE_MIN_DISTANCE,
                locationListener
            )

            _trackingState.value = TrackingState.STARTED
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    enum class TrackingState {
        NOT_STARTED,
        STARTED,
        PAUSED,
        FINISHED
    }
}