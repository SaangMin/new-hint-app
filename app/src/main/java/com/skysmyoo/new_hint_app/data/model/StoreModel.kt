package com.skysmyoo.new_hint_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(
    tableName = "saved_store_models"
)
@JsonClass(generateAdapter = true)
data class StoreModel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "storeCode") val code: String,
    @ColumnInfo(name = "storeName") val storeName: String,
    @ColumnInfo(name = "storePassword") val storePassword: String,
    @ColumnInfo(name = "themeList") val themeList: List<ThemeModel> = emptyList(),
) : Serializable