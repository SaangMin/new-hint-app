package com.skysmyoo.new_hint_app.data.source.repository

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.local.StoreDataSource
import com.skysmyoo.new_hint_app.data.source.remote.StoreRemoteDataSource
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultSuccess
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val localDataSource: StoreDataSource,
    private val remoteDataSource: StoreRemoteDataSource,
) {

    suspend fun getStore(): StoreModel {
        return localDataSource.getStore()
    }

    suspend fun findStore(code: String): StoreModel {
        return when (val response = remoteDataSource.getStoreByCode(code)) {
            is ApiResultSuccess -> {
                val store = response.data.values.firstOrNull()
                if (store != null) {
                    localDataSource.deleteStore()
                    localDataSource.insertNewStore(store)
                    localDataSource.setStoreCode(code)
                    store
                } else {
                    localDataSource.getStore()
                }
            }

            else -> localDataSource.getStore()
        }
    }

    fun getStoreCode(): String? {
        return localDataSource.getStoreCode()
    }
}