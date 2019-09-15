package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.database.ApplicationDatabase
import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.repository.EventRepository
import pl.kamilszustak.hulapp.repository.SettingsRepository
import pl.kamilszustak.hulapp.repository.TrackRepository
import pl.kamilszustak.hulapp.repository.UserRepository

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository: SettingsRepository = SettingsRepository(application)
    private val userRepository: UserRepository = UserRepository(application)
    private val trackRepository: TrackRepository = TrackRepository(application)
    private val eventRepository: EventRepository = EventRepository(application)

    fun <T: Comparable<T>> getSettingValue(sharedPreferencesSettingsKey: SettingsRepository.SharedPreferencesSettingsKey, defaultValue: T): T {
        return settingsRepository.getValue(sharedPreferencesSettingsKey, defaultValue)
    }

    fun <T: Comparable<T>> setSettingValue(sharedPreferencesSettingsKey: SettingsRepository.SharedPreferencesSettingsKey, value: T) {
        settingsRepository.setValue(sharedPreferencesSettingsKey, value)
    }

    fun <T: Comparable<T>> setSettingsValues(vararg values: Pair<SettingsRepository.SharedPreferencesSettingsKey, T>) {
        settingsRepository.setValues(*values)
    }

    fun restoreDefaultSettings() {
        settingsRepository.restoreDefaultSettings()
    }

    fun clearDatabase() {
        val database = ApplicationDatabase(getApplication())

        viewModelScope.async {
            database.clearAllTables()
        }
    }

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

    fun insertAllTracks(tracks: List<Track>) {
        viewModelScope.launch {
            trackRepository.insertAll(tracks)
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

    fun insertAllEvents(events: List<Event>) {
        viewModelScope.launch {
            eventRepository.insertAll(events)
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