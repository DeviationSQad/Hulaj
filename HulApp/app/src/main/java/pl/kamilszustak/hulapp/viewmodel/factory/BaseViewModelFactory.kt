package pl.kamilszustak.hulapp.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.kamilszustak.hulapp.viewmodel.BaseViewModel
import pl.kamilszustak.hulapp.viewmodel.LoginViewModel
import pl.kamilszustak.hulapp.viewmodel.SignUpViewModel
import pl.kamilszustak.hulapp.viewmodel.TrackViewModel

class BaseViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(application)
            SignUpViewModel::class.java -> SignUpViewModel(application)
            TrackViewModel::class.java -> TrackViewModel(application)
            else -> BaseViewModel(application)
        } as T
    }
}