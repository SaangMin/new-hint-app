package com.skysmyoo.new_hint_app.utils

import com.skysmyoo.new_hint_app.data.model.HintModel
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.model.ThemeModel

object SampleData {

    val sampleHint =
        HintModel(
            code = "1234",
            hintName = "hinttest",
            hintContent = "content",
            resultContent = "result",
            progress = "50%"
        )

    val sampleTheme =
        ThemeModel(
            themeTime = 3600,
            themeTitle = "jack in the show",
            hintList = listOf(sampleHint)
        )

    val sampleStore =
        StoreModel(
            code = "point123",
            storeName = "포인트 나인",
            themeList = listOf(sampleTheme)
        )
}