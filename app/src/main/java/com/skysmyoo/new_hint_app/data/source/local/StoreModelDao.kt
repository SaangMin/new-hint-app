package com.skysmyoo.new_hint_app.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skysmyoo.new_hint_app.data.model.StoreModel

@Dao
interface StoreModelDao {

    @Insert
    suspend fun insertNewStore(store: StoreModel)

    @Query("DELETE FROM saved_store_models")
    suspend fun deleteStore()

    @Query("SELECT * FROM saved_store_models")
    suspend fun getStore(): StoreModel
}