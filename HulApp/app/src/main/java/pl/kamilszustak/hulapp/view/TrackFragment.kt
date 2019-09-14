package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_track.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.util.round
import pl.kamilszustak.hulapp.viewmodel.TrackViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory
import java.text.DecimalFormat

class TrackFragment : Fragment(R.layout.fragment_track) {

    private val LOCATION_PERMISSION_REQUEST = 1

    private lateinit var viewModel: TrackViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(TrackViewModel::class.java)
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
    }

    private fun setListeners() {
        trackButton.setOnClickListener {
            val currentTrackingState = viewModel.currentTrackingState.value
            currentTrackingState?.let {
                when (it) {
                    TrackViewModel.TrackingState.NOT_STARTED,
                    TrackViewModel.TrackingState.FINISHED -> {
                        viewModel.startTracking()
                    }

                    TrackViewModel.TrackingState.PAUSED -> {
                        viewModel.resumeTracking()
                    }

                    TrackViewModel.TrackingState.STARTED -> {
                        viewModel.pauseTracking()
                    }
                }
            }
        }

        trackButton.setOnLongClickListener {
            viewModel.stopTracking()
            changeTrackButtonIcon(TrackViewModel.TrackingState.FINISHED)
            trackTimeTextView.text = "00:00:00"

            true
        }
    }

    private fun changeTrackButtonIcon(trackingState: TrackViewModel.TrackingState) {
        val icon = when (trackingState) {
            TrackViewModel.TrackingState.NOT_STARTED, TrackViewModel.TrackingState.FINISHED -> {
                activity?.getDrawable(R.drawable.start_icon)
            }

            TrackViewModel.TrackingState.STARTED -> {
                activity?.getDrawable(R.drawable.pause_icon)
            }

            TrackViewModel.TrackingState.PAUSED -> {
                activity?.getDrawable(R.drawable.start_icon)
            }
        }

        trackButton.setImageDrawable(icon)
    }
}