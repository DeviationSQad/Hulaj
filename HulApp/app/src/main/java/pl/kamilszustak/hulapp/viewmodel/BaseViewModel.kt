package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.repository.EventRepository
import pl.kamilszustak.hulapp.repository.TrackRepository
import pl.kamilszustak.hulapp.repository.UserRepository

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository = UserRepository(application)
    private val trackRepository: TrackRepository = TrackRepository(application)
    private val eventRepository: EventRepository = EventRepository(application)

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.update(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.delete(user)
        }
    }

    fun getUser(): LiveData<User> {
        return userRepository.get()
    }

    fun insertTrack(track: Track) {
        viewModelScope.launch {
            trackRepository.insert(track)
        }
    }

    fun updateTrack(track: Track) {
        viewModelScope.launch {
            trackRepository.update(track)
        }
    }

    fun deleteTrack(track: Track) {
        viewModelScope.launch {
            trackRepository.delete(track)
        }
    }

    fun getAllTracks(): LiveData<List<Track>> {
        return trackRepository.getAll()
    }

    fun getTrackById(id: Int): LiveData<Track> {
        return trackRepository.getById(id)
    }

    fun insertEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.insert(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.update(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.delete(event)
        }
    }

    fun getAllEvents(): LiveData<List<Event>> {
        return eventRepository.getAll()
    }

    fun getEventById(id: Int): LiveData<Event> {
        return eventRepository.getById(id)
    }
}