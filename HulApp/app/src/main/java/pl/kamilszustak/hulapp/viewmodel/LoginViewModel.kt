package pl.kamilszustak.hulapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kamilszustak.hulapp.model.network.LoginRequest
import pl.kamilszustak.hulapp.network.HulAppService
import pl.kamilszustak.hulapp.network.RetrofitClient
import timber.log.Timber

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus>
        get() = _loginStatus

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        val service = RetrofitClient.createService(HulAppService::class.java)

        viewModelScope.launch {
            val loginResponse = service.login(loginRequest)
            if (loginResponse.isSuccessful) {
                Timber.i("Successful")
                val response = loginResponse.body()
                response?.let {
                    Timber.i(it.toString())
                    insertUser(it.user)
                }
                _loginStatus.value = LoginStatus.LOGGED_IN
            } else {
                Timber.i("Error")
                _loginStatus.value = LoginStatus.LOGIN_ERROR
            }
        }
    }

    enum class LoginStatus {
        LOGGED_IN,
        LOGIN_ERROR
    }
}