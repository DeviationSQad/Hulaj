package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_event.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.viewmodel.CreateEventViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory
import timber.log.Timber
import java.text.SimpleDateFormat

class CreateEventFragment : Fragment(R.layout.fragment_create_event) {

    private lateinit var viewModel: CreateEventViewModel

    private var currentUserId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(CreateEventViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.eventCreationState.observe(this, Observer {
            when (it) {
                CreateEventViewModel.EventCreationState.EVENT_CREATED -> {
                    findNavController().navigateUp()
                }

                CreateEventViewModel.EventCreationState.EVENT_CREATION_ERROR -> {
                    view?.snackbar("Wystąpił błąd podczas dodawania wydarzenia")
                }
            }
        })

        viewModel.currentUser.observe(this, Observer {
            currentUserId = it.id
        })
    }

    private fun setListeners() {
        createEventButton.setOnClickListener {
            val createdEvent = createEvent()
            viewModel.postAndInsertEvent(createdEvent)
        }
    }

    private fun createEvent(): Event {
        val eventTitle = eventTitleEditText.text.toString()
        val dateString = eventDateEditText.text.toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val eventDate = dateFormat.parse(dateString)
        val eventPlaceName = eventPlaceNameEditText.text.toString()
        val eventAddress = eventAddressEditText.text.toString()
        val eventCity = eventCityEditText.text.toString()
        val eventCountry = eventCountryEditText.text.toString()
        val eventMaxAmountOfPeople = eventMaxAmountOfPeopleEditText.text.toString().toInt()
        val eventDescription = eventDescriptionEditText.text.toString()
        val isEventActive = true
        val eventUserId = currentUserId

        return Event(
            eventTitle,
            eventDescription,
            eventPlaceName,
            eventCountry,
            eventCity,
            eventAddress,
            eventDate,
            eventMaxAmountOfPeople,
            isEventActive,
            eventUserId
        )
    }
}