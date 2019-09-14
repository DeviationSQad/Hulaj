package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_track.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.viewmodel.TrackViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

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

        viewModel.trackingState.observe(this, Observer {
            val icon = when (it) {
                TrackViewModel.TrackingState.NOT_STARTED, TrackViewModel.TrackingState.FINISHED -> {
                    activity?.getDrawable(R.drawable.start_icon)
                }

                TrackViewModel.TrackingState.STARTED -> {
                    activity?.getDrawable(R.drawable.stop_icon)
                }

                TrackViewModel.TrackingState.PAUSED -> {
                    activity?.getDrawable(R.drawable.stop_icon)
                }

                else -> activity?.getDrawable(R.drawable.start_icon)
            }

            trackingButton.setImageDrawable(icon)
        })

    }

    private fun setListeners() {

    }
}