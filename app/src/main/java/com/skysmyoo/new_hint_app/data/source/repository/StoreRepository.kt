package com.skysmyoo.new_hint_app.data.source.repository

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.local.StoreDataSource
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val localDataSource: StoreDataSource,
){
    suspend fun insertStore(store:StoreModel) {
        localDataSource.insertNewStore(store)
    }

    suspend fun deleteStore() {
        localDataSource.deleteStore()
    }

    suspend fun getStore(): StoreModel {
        return localDataSource.getStore()
    }
}