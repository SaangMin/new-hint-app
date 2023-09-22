package com.skysmyoo.new_hint_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity (
    tableName = "hint_models"
)
@JsonClass(generateAdapter = true)
data class HintModel (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "hintName") val hintName: String,
    @ColumnInfo(name = "hintContent") val hintContent: String,
    @ColumnInfo(name = "hintImage") val hintImage: String? = null,
    @ColumnInfo(name = "resultContent") val resultContent: String,
    @ColumnInfo(name = "resultImage") val resultImage: String? = null,
    @ColumnInfo(name = "progress") val progress: String,
) : Serializable