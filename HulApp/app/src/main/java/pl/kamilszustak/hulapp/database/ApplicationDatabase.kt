package pl.kamilszustak.hulapp.database

import android.app.Application
import androidx.room.*
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.database.dao.EventDao
import pl.kamilszustak.hulapp.database.dao.TrackDao
import pl.kamilszustak.hulapp.database.dao.UserDao
import pl.kamilszustak.hulapp.model.Event

@Database(
    entities = [
        User::class,
        Track::class,
        Event::class
    ],
    version = 1,
    exportSchema = true)
@TypeConverters(Converters::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getTrackDao(): TrackDao
    abstract fun getEventDao(): EventDao

    companion object {
        private var INSTANCE: ApplicationDatabase? = null

        operator fun invoke(application: Application): ApplicationDatabase {
            return INSTANCE ?: synchronized(ApplicationDatabase::class) {
                build(application).also {
                    INSTANCE = it
                }
            }
        }

        private fun build(application: Application): ApplicationDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                ApplicationDatabase::class.java,
                application.applicationContext.getString(R.string.database_name)
            ).build()
        }
    }
}