package pl.kamilszustak.hulapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import pl.kamilszustak.hulapp.database.ApplicationDatabase
import pl.kamilszustak.hulapp.database.dao.UserDao
import pl.kamilszustak.hulapp.model.User

class UserRepository(application: Application) {

    private val userDao: UserDao = ApplicationDatabase(application).getUserDao()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    fun get(): LiveData<User> {
        return userDao.get()
    }
}