package pl.kamilszustak.hulapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_profile.*
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.model.User
import pl.kamilszustak.hulapp.viewmodel.ProfileViewModel
import pl.kamilszustak.hulapp.viewmodel.factory.BaseViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initializeViewModel()
        observeViewModel()
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOutItem -> {
                viewModel.logOut()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initializeViewModel() {
        activity?.let {
            val factory = BaseViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)
        }
    }

    private fun observeViewModel() {
        viewModel.isUserLoggedOut.observe(this, Observer {
            if (it) {
                val intent = Intent(context, AuthenticationActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        viewModel.currentUser.observe(this, Observer {
            loadUserDetails(it)
        })
    }

    private fun setListeners() {

    }

    private fun loadUserDetails(user: User) {
        userNameTextView.text = "${user.name} ${user.surname}"

        user.profile?.let {
            Glide.with(this)
                .load(it.photoUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(150))
                .into(userProfilePhotoImageView)


            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            userBirthDateTextView.text = dateFormat.format(it.birthDate)
            userCityTextView.text = it.city
            userCountryTextView.text = it.country
            userBioTextView.text = it.bio
        }
    }
}