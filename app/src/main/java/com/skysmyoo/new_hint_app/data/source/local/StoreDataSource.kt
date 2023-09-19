package com.skysmyoo.new_hint_app.data.source.local

import com.skysmyoo.new_hint_app.data.model.StoreModel
import javax.inject.Inject

class StoreDataSource @Inject constructor(
    private val storeModelDao: StoreModelDao,
) {
    suspend fun insertNewStore(store: StoreModel) {
        storeModelDao.insertNewStore(store)
    }

    suspend fun deleteStore() {
        storeModelDao.deleteStore()
    }

    suspend fun getStore(): StoreModel {
        return storeModelDao.getStore()
    }
}