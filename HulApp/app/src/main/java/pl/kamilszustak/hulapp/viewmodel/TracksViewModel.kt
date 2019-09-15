package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.constant.DEFAULT_CURRENT_USER_ID
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import pl.kamilszustak.hulapp.repository.SettingsRepository
import timber.log.Timber

class TracksViewModel(application: Application) : BaseViewModel(application) {

    val tracks: LiveData<List<Track>> = getAllTracks()

    init {
        syncTracks()
    }

    private fun syncTracks() {
        val service = RetrofitClient.createService(HulAppService::class.java)
        val userId = getSettingValue(SettingsRepository.SharedPreferencesSettingsKey.CURRENT_USER_ID, DEFAULT_CURRENT_USER_ID)

        viewModelScope.launch {
            val response = service.getTracksByUserId(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Timber.i("Tracks $it")
                    insertAllTracks(it)
                }
            } else {
                Timber.i("Request unsuccessful ")
            }
        }
    }
}