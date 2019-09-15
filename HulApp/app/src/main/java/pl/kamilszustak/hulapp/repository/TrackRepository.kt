package pl.kamilszustak.hulapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.database.ApplicationDatabase
import pl.kamilszustak.hulapp.database.dao.TrackDao
import pl.kamilszustak.hulapp.model.Track

class TrackRepository(application: Application) {

    private val trackDao: TrackDao = ApplicationDatabase(application).getTrackDao()

    suspend fun insert(track: Track) {
        trackDao.insert(track)
    }

    suspend fun insertAll(tracks: List<Track>) {
        trackDao.insertAll(tracks)
    }

    suspend fun update(track: Track) {
        trackDao.update(track)
    }

    suspend fun delete(track: Track) {
        trackDao.delete(track)
    }

    fun getAll(): LiveData<List<Track>> {
        return trackDao.getAll()
    }

    fun getById(id: Int): LiveData<Track> {
        return trackDao.getById(id)
    }
}