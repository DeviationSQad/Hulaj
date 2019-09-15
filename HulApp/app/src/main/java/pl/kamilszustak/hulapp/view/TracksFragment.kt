package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tracks.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.adapter.TracksRecyclerViewAdapter
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.viewmodel.TracksViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private lateinit var viewModel: TracksViewModel
    private lateinit var recyclerViewAdapter: TracksRecyclerViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViewAdapter()
        initializeRecyclerView()
        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeRecyclerViewAdapter() {
        val listener = object: TracksRecyclerViewAdapter.TrackViewHolder.OnTrackClickListener {
            override fun onTrackClick(track: Track) {
                // TODO: Przejście do szczegółów trasy
            }
        }

        recyclerViewAdapter = TracksRecyclerViewAdapter(listener)
    }

    private fun initializeRecyclerView() {
        tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(TracksViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.tracks.observe(this, Observer {
            recyclerViewAdapter.updateTracksList(it)
        })
    }

    private fun setListeners() {
        trackLocationButton.setOnClickListener {
            val direction = TracksFragmentDirections.actionTracksFragmentToTrackLocationFragment()
            findNavController().navigate(direction)
        }
    }
}