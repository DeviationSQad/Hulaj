package pl.kamilszustak.hulapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.design.snackbar
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.viewmodel.LoginViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.loginStatus.observe(this, Observer {
            when (it) {
                LoginViewModel.LoginStatus.LOGGED_IN -> {
                    navigateToMainActivity()
                }

                LoginViewModel.LoginStatus.LOGIN_ERROR -> {
                    view?.snackbar("Nieprawidłowy email lub hasło")
                }
            }
        })
    }

    private fun setListeners() {
        signUpButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}