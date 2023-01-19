package com.example.kite.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object PrefManager {
    private lateinit var pref: SharedPreferences
    private const val PREF_NAME = "KITE_SHARED_PREF"

    fun with(application: Application) {
        pref = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
  /*   fun <T> put(`object`: T, key: String) {
         val jsonString = GsonBuilder().create().toJson(`object`)
         preferences.edit().putString(key, jsonString).apply()
     }

      inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }*/


}