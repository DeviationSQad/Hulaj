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
            TrackViewModel::class.java -> TrackViewModel(application)
            EventsViewModel::class.java -> EventsViewModel(application)
            else -> BaseViewModel(application)
        } as T
    }
}