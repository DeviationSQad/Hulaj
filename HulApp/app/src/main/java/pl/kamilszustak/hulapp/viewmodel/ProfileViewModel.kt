package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.kamilszustak.hulapp.repository.SettingsRepository

class ProfileViewModel(application: Application) : BaseViewModel(application) {

    private val _isUserLoggedOut = MutableLiveData<Boolean>(false)
    val isUserLoggedOut: LiveData<Boolean>
        get() = _isUserLoggedOut

    fun logOut() {
        setSettingValue(SettingsRepository.SharedPreferencesSettingsKey.IS_USER_LOGGED_IN, false)
        clearDatabase()
        _isUserLoggedOut.value = true
    }
}