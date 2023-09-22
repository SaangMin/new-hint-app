package com.skysmyoo.new_hint_app.utils

object Admin {

    private const val masterPassword = "0945"

    fun isCorrectMasterPassword(password: String): Boolean {
        return password == masterPassword
    }
}