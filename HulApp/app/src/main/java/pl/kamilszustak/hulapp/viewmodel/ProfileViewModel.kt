package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import pl.kamilszustak.hulapp.repository.SettingsRepository
import timber.log.Timber

class ProfileViewModel(application: Application) : BaseViewModel(application) {

    val currentUser: LiveData<User> = getUser()

    private val _isUserLoggedOut = MutableLiveData<Boolean>(false)
    val isUserLoggedOut: LiveData<Boolean>
        get() = _isUserLoggedOut

    fun logOut() {
        val service = RetrofitClient.createService(HulAppService::class.java)
        viewModelScope.launch {
            val response = service.logout()
            if (response.isSuccessful) {
                setSettingValue(SettingsRepository.SharedPreferencesSettingsKey.IS_USER_LOGGED_IN, false)
                clearDatabase()
                _isUserLoggedOut.value = true
                Timber.i("Database cleared")
            } else {
                Timber.i("Request unsuccessful")
            }
        }
    }
}