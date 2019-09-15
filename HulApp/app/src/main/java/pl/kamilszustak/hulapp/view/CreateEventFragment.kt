package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_create_event.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.constant.DEFAULT_CURRENT_USER_ID
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.repository.SettingsRepository
import pl.kamilszustak.hulapp.viewmodel.CreateEventViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory
import timber.log.Timber
import java.text.SimpleDateFormat

class CreateEventFragment : Fragment(R.layout.fragment_create_event) {

    private lateinit var viewModel: CreateEventViewModel

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
        val eventUserId = viewModel.getSettingValue(SettingsRepository.SharedPreferencesSettingsKey.CURRENT_USER_ID, DEFAULT_CURRENT_USER_ID)

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