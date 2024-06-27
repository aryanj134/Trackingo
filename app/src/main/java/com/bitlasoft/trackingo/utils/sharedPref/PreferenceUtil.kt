package com.bitlasoft.trackingo.utils.sharedPref

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.StrictMode

object PreferenceUtil {

    lateinit var sharedPreferences: SharedPreferences

    private const val PREF_NAME = "MyAppPrefs"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Add more methods as needed for other preferences
}