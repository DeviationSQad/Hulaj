package pl.kamilszustak.hulapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kamilszustak.hulapp.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun get(): LiveData<User>
}