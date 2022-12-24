package com.exxuslee.ui.main

import android.content.Context
import androidx.preference.PreferenceManager.getDefaultSharedPreferences


class MyPreferences(context: Context) {

    companion object {
        private const val DARK_STATUS = "DARK_STATUS"
    }

    private val preferences = getDefaultSharedPreferences(context)

    var darkMode = preferences?.getInt(DARK_STATUS, 0)
        set(value) = value?.let { preferences?.edit()?.putInt(DARK_STATUS, it)?.apply() }!!

}