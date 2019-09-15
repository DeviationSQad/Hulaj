package pl.kamilszustak.hulapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kamilszustak.hulapp.model.Track

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<Track>)

    @Update
    suspend fun update(track: Track)

    @Delete
    suspend fun delete(track: Track)

    @Query("SELECT * FROM track")
    fun getAll(): LiveData<List<Track>>

    @Query("SELECT * FROM track WHERE id = :id")
    fun getById(id: Int): LiveData<Track>
}