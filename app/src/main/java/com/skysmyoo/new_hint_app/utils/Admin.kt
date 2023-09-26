package com.skysmyoo.new_hint_app.utils

import com.skysmyoo.new_hint_app.HintApplication

object Admin {

    private const val masterPassword = "0945"
    private val preferencesManager = HintApplication.preferencesManager

    fun isCorrectMasterPassword(password: String): Boolean {
        return password == masterPassword
    }

    fun isCorrectStorePassword(password: String): Boolean {
        return password == preferencesManager.getPassword()
    }
}