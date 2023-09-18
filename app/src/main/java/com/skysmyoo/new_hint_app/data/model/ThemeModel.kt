package com.skysmyoo.new_hint_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(
    tableName = "theme_models"
)
@JsonClass(generateAdapter = true)
data class ThemeModel (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "themeTitle") val themeTitle: String,
    @ColumnInfo(name = "themeTime")val themeTime: Int,
    @ColumnInfo(name = "hintList")val hintList: List<HintModel> = emptyList(),
)