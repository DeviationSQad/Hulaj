package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.model.Event

class EventsViewModel(application: Application) : BaseViewModel(application) {

    val events: LiveData<List<Event>> = getAllEvents()

    init {
        addSomeTestEvents()
    }

    private fun addSomeTestEvents() {
        for (i in 0..10) {
            insertEvent(Event(i.toString(), i.toString()))
        }
    }
}