package com.example.kite

import android.app.Application
import com.example.kite.base.network.ApiClient
import com.example.kite.utils.PrefManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PrefManager.with(this)
        ApiClient.initRetrofit()
    }
}