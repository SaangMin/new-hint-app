package com.skysmyoo.new_hint_app

import android.app.Application
import com.skysmyoo.new_hint_app.data.source.local.SharedPreferencesManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HintApplication : Application() {
    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
        preferencesManager = sharedPreferencesManager
    }

    companion object {
        lateinit var preferencesManager: SharedPreferencesManager
    }
}