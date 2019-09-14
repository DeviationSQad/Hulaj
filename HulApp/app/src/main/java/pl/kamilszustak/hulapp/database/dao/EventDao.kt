package pl.kamilszustak.hulapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kamilszustak.hulapp.model.Event

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM event")
    fun getAll(): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun getById(id: Int): LiveData<Event>
}