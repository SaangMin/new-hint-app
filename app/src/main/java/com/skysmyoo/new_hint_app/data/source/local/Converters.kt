package com.skysmyoo.new_hint_app.data.source.local

import androidx.room.TypeConverter
import com.skysmyoo.new_hint_app.data.model.HintModel
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.model.ThemeModel
import com.skysmyoo.new_hint_app.di.AppModule
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

class Converters {
    private val moshi = AppModule.moshi

    private val hintListAdapter: JsonAdapter<List<HintModel>> = moshi.adapter(
        Types.newParameterizedType(
            List::class.java,
            HintModel::class.java
        )
    )

    private val themeListAdapter: JsonAdapter<List<ThemeModel>> = moshi.adapter(
        Types.newParameterizedType(
            List::class.java,
            ThemeModel::class.java
        )
    )

    @TypeConverter
    fun hintFromString(value: String): HintModel {
        return moshi.adapter(HintModel::class.java).fromJson(value)!!
    }

    @TypeConverter
    fun fromHintModel(hintModel: HintModel): String {
        return moshi.adapter(HintModel::class.java).toJson(hintModel)
    }

    @TypeConverter
    fun themeFromString(value: String): ThemeModel {
        return moshi.adapter(ThemeModel::class.java).fromJson(value)!!
    }

    @TypeConverter
    fun fromThemeModel(themeModel: ThemeModel): String {
        return moshi.adapter(ThemeModel::class.java).toJson(themeModel)
    }

    @TypeConverter
    fun storeFromString(value: String): StoreModel {
        return moshi.adapter(StoreModel::class.java).fromJson(value)!!
    }

    @TypeConverter
    fun fromStoreModel(storeModel: StoreModel): String {
        return moshi.adapter(StoreModel::class.java).toJson(storeModel)
    }

    @TypeConverter
    fun hintListFromString(value: String): List<HintModel>? {
        return hintListAdapter.fromJson(value)
    }

    @TypeConverter
    fun fromHintList(hintList: List<HintModel>): String {
        return hintListAdapter.toJson(hintList)
    }

    @TypeConverter
    fun themeListFromString(value: String): List<ThemeModel>? {
        return themeListAdapter.fromJson(value)
    }

    @TypeConverter
    fun fromThemeList(themeList: List<ThemeModel>): String {
        return themeListAdapter.toJson(themeList)
    }
}