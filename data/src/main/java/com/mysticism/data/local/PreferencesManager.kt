package com.mysticism.data.local

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun isDataDownloaded(): Boolean {
        return preferences.getBoolean("data_downloaded", false)
    }

    fun setDataDownloaded(isDownloaded: Boolean) {
        preferences.edit().putBoolean("data_downloaded", isDownloaded).apply()
    }
}
