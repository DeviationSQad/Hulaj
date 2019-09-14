package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.viewmodel.LoginViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
        }
    }

    private fun setListeners() {
        signUpButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        loginButton.setOnClickListener {

        }
    }
}