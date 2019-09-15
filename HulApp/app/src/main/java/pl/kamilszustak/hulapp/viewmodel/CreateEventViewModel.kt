package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import timber.log.Timber

class CreateEventViewModel(application: Application) : BaseViewModel(application) {

    private val _eventCreationState = MutableLiveData<EventCreationState>()
    val eventCreationState: LiveData<EventCreationState>
        get() = _eventCreationState

    val currentUser: LiveData<User> = getUser()

    fun postAndInsertEvent(event: Event) {
        Timber.i(event.toString())
        val service = RetrofitClient.createService(HulAppService::class.java)

        viewModelScope.launch {
            val response = service.postEvent(event)
            if (response.isSuccessful) {
                response.body()?.let {
                    event.id = it.id
                }
                insertEvent(event)
                _eventCreationState.value = EventCreationState.EVENT_CREATED
            } else {
                Timber.i("Request unsuccessful")
                _eventCreationState.value = EventCreationState.EVENT_CREATION_ERROR
            }
        }
    }

    enum class EventCreationState {
        EVENT_CREATED,
        EVENT_CREATION_ERROR
    }
}