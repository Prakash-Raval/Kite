package com.example.kite.base.utils

import android.content.SharedPreferences


object MyPreference {
    var mSharedPref: SharedPreferences? = null


    fun getValueString(
        key: String,
        defaultValue: String
    ): String? {
        return mSharedPref?.getString(key, defaultValue)
    }


}