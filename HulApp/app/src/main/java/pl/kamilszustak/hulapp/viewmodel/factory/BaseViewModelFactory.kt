package pl.kamilszustak.hulapp.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.kamilszustak.hulapp.viewmodel.*

class BaseViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(application)
            SignUpViewModel::class.java -> SignUpViewModel(application)
            TrackLocationViewModel::class.java -> TrackLocationViewModel(application)
            EventsViewModel::class.java -> EventsViewModel(application)
            CreateEventViewModel::class.java -> CreateEventViewModel(application)
            ProfileViewModel::class.java -> ProfileViewModel(application)
            TracksViewModel::class.java -> TracksViewModel(application)
            else -> BaseViewModel(application)
        } as T
    }
}