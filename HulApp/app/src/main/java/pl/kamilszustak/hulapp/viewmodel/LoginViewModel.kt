package pl.kamilszustak.hulapp.viewmodel

import android.app.Application

class LoginViewModel(application: Application) : BaseViewModel(application) {


    enum class LoginStatus {
        LOGGED_IN,
        LOGIN_ERROR
    }
}