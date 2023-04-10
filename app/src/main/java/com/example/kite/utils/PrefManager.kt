package com.example.kite.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.kite.profile.model.ViewProfileResponse
import com.google.gson.GsonBuilder

object PrefManager {

    lateinit var preferences: SharedPreferences
    private const val PREF_NAME = "KITE_SHARED_PREF"

    fun with(application: Application) {
        preferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun <T> put(`object`: T, key: String) {

        val jsonString = GsonBuilder().create().toJson(`object`)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun remove() {
        preferences.edit().clear().apply()
    }


}