package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import timber.log.Timber

class EventsViewModel(application: Application) : BaseViewModel(application) {

    val events: LiveData<List<Event>> = getAllEvents()

    init {
        syncEvents()
    }

    private fun syncEvents() {
        val service = RetrofitClient.createService(HulAppService::class.java)

        viewModelScope.launch {
            val response = service.getAllEvents()
            if (response.isSuccessful) {
                response.body()?.let {
                    insertAllEvents(it)
                }
            } else {
                Timber.i("Request unsuccessful ")
            }
        }
    }

    private fun addSomeTestEvents() {
        for (i in 0..10) {
            insertEvent(Event(i.toString(), i.toString()))
        }
    }
}