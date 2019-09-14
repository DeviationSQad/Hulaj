package pl.kamilszustak.hulapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import pl.kamilszustak.hulapp.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBottomNavigationView()
    }

    private fun initializeBottomNavigationView() {
        val navController = findNavController(R.id.mainNavHostFragment)
        bottomNavigationView.setupWithNavController(navController)
    }
}