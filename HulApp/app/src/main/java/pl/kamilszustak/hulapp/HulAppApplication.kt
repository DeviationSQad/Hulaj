package pl.kamilszustak.hulapp

import android.app.Application
import timber.log.Timber

class HulAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}