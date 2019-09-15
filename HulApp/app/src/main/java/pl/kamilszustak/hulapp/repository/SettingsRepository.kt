package pl.kamilszustak.hulapp.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import pl.kamilszustak.hulapp.R
import pl.kamilszustak.hulapp.constant.DEFAULT_IS_USER_LOGGED_IN
import pl.kamilszustak.hulapp.database.ApplicationDatabase

class SettingsRepository(
    private val application: Application
) {
    private val sharedPreferences: SharedPreferences

    init {
        val sharedPreferencesName = getStringResource(R.string.shared_preferences_name)
        sharedPreferences = getSharedPreferences(sharedPreferencesName)
    }

    fun <T: Comparable<T>> getValue(sharedPreferencesSettingsKey: SharedPreferencesSettingsKey, defaultValue: T): T {
        val key = getStringResource(sharedPreferencesSettingsKey.stringResourceId)

        return when (defaultValue) {
            is Int -> sharedPreferences.getInt(key, defaultValue)
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
            is String -> sharedPreferences.getString(key, defaultValue)
            is Float -> sharedPreferences.getFloat(key, defaultValue)
            is Long -> sharedPreferences.getLong(key, defaultValue)
            else -> defaultValue
        } as T
    }

    fun <T: Comparable<T>> setValue(sharedPreferencesSettingsKey: SharedPreferencesSettingsKey, value: T) {
        val key = getStringResource(sharedPreferencesSettingsKey.stringResourceId)

        sharedPreferences.edit {
            when (value) {
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
            }
        }
    }

    fun <T: Comparable<T>> setValues(vararg values: Pair<SharedPreferencesSettingsKey, T>) {
        values.forEach {
            setValue(it.first, it.second)
        }
    }

    fun restoreDefaultSettings() {
        sharedPreferences.edit {
            var key = getStringResource(SharedPreferencesSettingsKey.IS_USER_LOGGED_IN.stringResourceId)
            putBoolean(key, DEFAULT_IS_USER_LOGGED_IN)
        }
    }

    private fun getStringResource(stringResourceId: Int): String =
        application.resources.getString(stringResourceId)

    private fun getSharedPreferences(sharedPreferencesName: String): SharedPreferences =
        application.applicationContext.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    enum class SharedPreferencesSettingsKey(val stringResourceId: Int) {
        IS_USER_LOGGED_IN(R.string.shared_preferences_is_user_logged_in),
        CURRENT_USER_ID(R.string.shared_preferences_current_user_id)
    }
}