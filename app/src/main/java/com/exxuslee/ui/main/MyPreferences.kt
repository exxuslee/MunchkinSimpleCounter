package com.exxuslee.ui.main

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager.getDefaultSharedPreferences


class MyPreferences(context: Context) {

    private val preferences = getDefaultSharedPreferences(context)

    fun store(key: String?, `val`: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, `val`)
        editor.apply()
    }

    operator fun get(key: String?, valDefault: Boolean): Boolean {
        return preferences.getBoolean(key, valDefault)
    }
}