package com.skysmyoo.new_hint_app.data.source.local

import android.content.Context
import android.content.SharedPreferences
import com.skysmyoo.new_hint_app.utils.Constants
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.KEY_MY_PREFERENCES, Context.MODE_PRIVATE)

    fun setStoreCode(title: String) {
        sharedPreferences.edit().putString(Constants.KEY_STORE_CODE, title).apply()
    }

    fun getStoreName(): String? {
        return sharedPreferences.getString(Constants.KEY_STORE_CODE, "")
    }

    fun setPassword(password: String) {
        sharedPreferences.edit().putString(Constants.KEY_PASSWORD, password).apply()
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(Constants.KEY_PASSWORD, "9999")
    }
}