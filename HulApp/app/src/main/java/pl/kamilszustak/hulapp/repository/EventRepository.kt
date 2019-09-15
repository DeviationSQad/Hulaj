package pl.kamilszustak.hulapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.database.ApplicationDatabase
import pl.kamilszustak.hulapp.database.dao.EventDao
import pl.kamilszustak.hulapp.model.Event

class EventRepository(application: Application) {

    private val eventDao: EventDao = ApplicationDatabase(application).getEventDao()

    suspend fun insert(event: Event) {
        eventDao.insert(event)
    }

    suspend fun insertAll(events: List<Event>) {
        eventDao.insertAll(events)
    }

    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }

    fun getAll(): LiveData<List<Event>> {
        return eventDao.getAll()
    }

    fun getById(id: Int): LiveData<Event> {
        return eventDao.getById(id)
    }
}