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
import androidx.lifecycle.viewModelScope
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import pl.kamilszustak.hulapp.util.getSystemService
import timber.log.Timber
import java.util.*

class TrackLocationViewModel(application: Application) : BaseViewModel(application) {

    private val LOCATION_UPDATE_MIN_INTERVAL: Long = 3000
    private val LOCATION_UPDATE_MIN_DISTANCE: Float = 10.0F

    private val locationManager = application.applicationContext.getSystemService<LocationManager>()

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    /**
     * Current track length in kilometers
     */
    private val _trackLength = MutableLiveData<Double>(0.0)
    val trackLength: LiveData<Double>
        get() = _trackLength

    /**
     * Current tract duration in seconds
     */
    private val _trackDuration = MutableLiveData<Long>(0)
    val trackDuration: LiveData<Long>
        get() = _trackDuration

    private val _currentTrackingState = MutableLiveData<TrackingState>(TrackingState.NOT_STARTED)
    val currentTrackingState: LiveData<TrackingState>
        get() = _currentTrackingState

    private val _isLocationPermissionGranted = MutableLiveData<Boolean>()
    val isLocationPermissionGranted: LiveData<Boolean>
        get() = _isLocationPermissionGranted

    private lateinit var currentTrack: Track

    private lateinit var previousLocation: Location
    private var isFirstLocationUpdate = true
    private lateinit var stopwatch: Stopwatch

    private val locationListener = object: LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (isFirstLocationUpdate) {
                location?.let {
                    previousLocation = it
                }
                isFirstLocationUpdate = false
            } else {
                val newDistance = previousLocation.distanceTo(location) / 1000
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
        val currentTrackingState = _currentTrackingState.value

        if (currentTrackingState == TrackingState.NOT_STARTED ||
            currentTrackingState == TrackingState.FINISHED) {

            currentTrack = Track(
                startDate = Date()
            )
            initializeTimer()
            stopwatch.start()
            requestLocationUpdates()
            _currentTrackingState.value = TrackingState.STARTED

            Timber.i("Tracking started")
        }
    }

    fun pauseTracking() {
        val currentTrackingState = _currentTrackingState.value

        if (currentTrackingState == TrackingState.STARTED) {
            removeLocationUpdates()
            if (stopwatch.isStarted)
                stopwatch.pause()
            _currentTrackingState.value = TrackingState.PAUSED

            Timber.i("Tracking paused")
        }
    }

    fun resumeTracking() {
        val currentTrackingState = _currentTrackingState.value

        if (currentTrackingState == TrackingState.PAUSED) {
            requestLocationUpdates()
            if (stopwatch.isPaused)
                stopwatch.resume()
            _currentTrackingState.value = TrackingState.STARTED

            Timber.i("Tracking resumed")
        }
    }

    fun stopTracking() {
        val currentTrackingState = _currentTrackingState.value

        if (currentTrackingState == TrackingState.PAUSED) {
            removeLocationUpdates()
            if (stopwatch.isStarted)
                stopwatch.stop()
            currentTrack.apply {
                endDate = Date()
                length = _trackLength.value ?: 0.0
                userId = _currentUser.value?.id ?: 0
            }
            _currentTrackingState.value = TrackingState.FINISHED
            postAndInsertTrack(currentTrack)

            Timber.i("Tracking stopped")
        }
    }

    private fun postAndInsertTrack(track: Track) {
        val service = RetrofitClient.createService(HulAppService::class.java)

        viewModelScope.launch {
            val response = service.postTrack(track)
            if (response.isSuccessful) {
                response.body()?.let {
                    track.id = it.id
                }
                insertTrack(track)
            } else {
                Timber.i("Response unsuccessful")
            }
        }
    }

    private fun initializeTimer() {
        stopwatch = Stopwatch()
        stopwatch.setOnTickListener { stopwatch ->
            stopwatch?.let {
                _trackDuration.value = it.elapsedTime / 1000
            }
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

            _currentTrackingState.value = TrackingState.STARTED
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    private fun removeLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    override fun onCleared() {
        super.onCleared()
        removeLocationUpdates()
    }

    enum class TrackingState {
        NOT_STARTED,
        STARTED,
        PAUSED,
        FINISHED
    }
}