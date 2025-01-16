package com.skysmyoo.new_hint_app.utils

import com.skysmyoo.new_hint_app.data.model.ThemeModel

object Constants {
    const val KEY_MY_PREFERENCES = "key_my_preferences"
    const val KEY_STORE_CODE = "key_store_code"
    const val KEY_TARGET_PASSWORD = "key_target_password"
    const val KEY_TARGET_CHANGING_PASSWORD = "key_target_changing_password"
    const val KEY_TARGET_ADDING_THEME = "key_target_adding_theme"
    const val KEY_PASSWORD = "key_password"
    const val KEY_TARGET_CHANGING_TITLE = "key_target_changing_title"
    const val KEY_TARGET_DELETE_THEME = "key_target_delete_theme"
    const val NOTIFICATION_CHANNEL = "TIMER_CHANNEL"
    const val NOTIFICATION_ID = 1
    const val KEY_SIGN = "key_sign"
    const val KEY_CUSTOMER_NAME = "key_customer_name"
    const val WRONG_HINT_CODE_MSG = "잘못된 코드입니다."
    const val KEY_LAST_PHONE_NUMBER = "key_last_phone_number"
    const val PATH_STORES = "stores"
    val SAMPLE_THEME = ThemeModel(themeTitle = "인도네시아", themeTime = 3600, hintList = emptyList())
    const val UDP_IP = "192.168.0.119"
}