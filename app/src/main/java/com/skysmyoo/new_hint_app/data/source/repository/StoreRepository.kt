package com.skysmyoo.new_hint_app.data.source.repository

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.local.StoreDataSource
import com.skysmyoo.new_hint_app.data.source.remote.StoreRemoteDataSource
import com.skysmyoo.new_hint_app.utils.SampleData
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val localDataSource: StoreDataSource,
    private val remoteDataSource: StoreRemoteDataSource,
) {

    suspend fun getStore(): StoreModel {
        return localDataSource.getStore()
    }

    suspend fun findStore(code: String): StoreModel? {
        val storeModel = remoteDataSource.getStoreByCode(code)
        if (storeModel != null) {
            localDataSource.insertNewStore(storeModel)
            localDataSource.setStoreCode(code)
        }
        return storeModel
    }

    suspend fun findStoreFromLocal(): StoreModel {
        return localDataSource.getStore()
    }

    fun getStoreCode(): String? {
        return localDataSource.getStoreCode()
    }

    suspend fun putSample() {
        remoteDataSource.putSample(SampleData.sampleStore)
    }
}