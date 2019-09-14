package pl.kamilszustak.hulapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_sign_up.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.viewmodel.SignUpViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var viewModel: SignUpViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        setListeners()
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(SignUpViewModel::class.java)
        }
    }

    private fun setListeners() {
        signUpButton.setOnClickListener {

        }
    }
}