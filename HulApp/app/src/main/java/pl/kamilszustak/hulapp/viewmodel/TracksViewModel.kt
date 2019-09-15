package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import timber.log.Timber

class TracksViewModel(application: Application) : BaseViewModel(application) {

    val tracks: LiveData<List<Track>> = getAllTracks()

    init {
        syncTracks()
    }

    private fun syncTracks() {
        val service = RetrofitClient.createService(HulAppService::class.java)

        viewModelScope.launch {
            val response = service.getAllTracks()
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