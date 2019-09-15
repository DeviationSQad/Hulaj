package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import timber.log.Timber

class TracksViewModel(application: Application) : BaseViewModel(application) {

    val tracks: LiveData<List<Track>> = getAllTracks()
    val currentUser: LiveData<User> = getUser()

    init {
        syncTracks()
    }

    private fun syncTracks() {
        val service = RetrofitClient.createService(HulAppService::class.java)
        val userId = currentUser.value?.id ?: 0

        viewModelScope.launch {
            val response = service.getTrackByUserId(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    insertAllTracks(it)
                }
            } else {
                Timber.i("Request unsuccessful ")
            }
        }
    }
}