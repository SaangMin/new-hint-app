package com.skysmyoo.new_hint_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skysmyoo.new_hint_app.data.model.StoreModel

@Database(entities = [StoreModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storeModelDao(): StoreModelDao
}