package pl.kamilszustak.hulapp.viewmodel

import android.app.Application

class SignUpViewModel(application: Application) : BaseViewModel(application) {


    enum class SignUpStatus {
        SIGNED_UP,
        SIGN_UP_ERROR
    }
}