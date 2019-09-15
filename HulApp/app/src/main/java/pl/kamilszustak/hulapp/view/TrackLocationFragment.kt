package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_track_location.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.util.round
import pl.kamilszustak.hulapp.viewmodel.TrackLocationViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class TrackLocationFragment : Fragment(R.layout.fragment_track_location) {

    private val LOCATION_PERMISSION_REQUEST = 1

    private lateinit var viewModel: TrackLocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(TrackLocationViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.isLocationPermissionGranted.observe(this, Observer {
            if (!it) {
                activity?.let { activity ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST
                    )
                }
            }
        })

        viewModel.currentTrackingState.observe(this, Observer {
            changeTrackButtonIcon(it)
        })

        viewModel.trackLength.observe(this, Observer {

            trackLengthTextView.text = it.round(2).toString()
        })

        viewModel.trackDuration.observe(this, Observer {
            val formattedTime = String.format(
                "%02d:%02d:%02d",
                it / 3600,
                (it % 3600) / 60,
                (it % 60)
            )
            trackTimeTextView.text = formattedTime
        })

        viewModel.currentUser.observe(this, Observer {
        })
    }

    private fun setListeners() {
        trackButton.setOnClickListener {
            val currentTrackingState = viewModel.currentTrackingState.value
            currentTrackingState?.let {
                when (it) {
                    TrackLocationViewModel.TrackingState.NOT_STARTED,
                    TrackLocationViewModel.TrackingState.FINISHED -> {
                        viewModel.startTracking()
                    }

                    TrackLocationViewModel.TrackingState.PAUSED -> {
                        viewModel.resumeTracking()
                    }

                    TrackLocationViewModel.TrackingState.STARTED -> {
                        viewModel.pauseTracking()
                    }
                }
            }
        }

        trackButton.setOnLongClickListener {
            viewModel.stopTracking()
            changeTrackButtonIcon(TrackLocationViewModel.TrackingState.FINISHED)
            trackTimeTextView.text = "00:00:00"

            true
        }
    }

    private fun changeTrackButtonIcon(trackingState: TrackLocationViewModel.TrackingState) {
        val icon = when (trackingState) {
            TrackLocationViewModel.TrackingState.NOT_STARTED, TrackLocationViewModel.TrackingState.FINISHED -> {
                activity?.getDrawable(R.drawable.start_icon)
            }

            TrackLocationViewModel.TrackingState.STARTED -> {
                activity?.getDrawable(R.drawable.pause_icon)
            }

            TrackLocationViewModel.TrackingState.PAUSED -> {
                activity?.getDrawable(R.drawable.start_icon)
            }
        }

        trackButton.setImageDrawable(icon)
    }
}