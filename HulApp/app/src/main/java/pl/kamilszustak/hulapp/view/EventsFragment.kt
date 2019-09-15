package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_events.*
import kotlinx.android.synthetic.main.fragment_tracks.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.adapter.EventsRecyclerViewAdapter
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.viewmodel.EventsViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class EventsFragment : Fragment(R.layout.fragment_events) {

    private lateinit var viewModel: EventsViewModel
    private lateinit var recyclerViewAdapter: EventsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViewAdapter()
        initializeRecyclerView()
        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeRecyclerViewAdapter() {
        val listener = object: EventsRecyclerViewAdapter.EventViewHolder.OnEventClickListener {
            override fun onEventClick(event: Event) {
                // TODO: Dodać przejście do widoku eventu
            }
        }

        recyclerViewAdapter = EventsRecyclerViewAdapter(listener)
    }

    private fun initializeRecyclerView() {
        eventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(EventsViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.events.observe(this, Observer {
            recyclerViewAdapter.updateEventsList(it)
        })
    }

    private fun setListeners() {
        createEventButton.setOnClickListener {
            val direction = EventsFragmentDirections.actionEventsFragmentToCreateEventFragment().actionId
            val extras = FragmentNavigatorExtras(
                it to "createEventButton"
            )
            findNavController().navigate(direction, null, null, extras)
        }
    }
}