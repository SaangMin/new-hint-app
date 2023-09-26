package com.skysmyoo.new_hint_app.data.source.repository

import com.skysmyoo.new_hint_app.data.model.HintModel
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.model.ThemeModel
import com.skysmyoo.new_hint_app.data.source.local.StoreDataSource
import com.skysmyoo.new_hint_app.data.source.remote.StoreRemoteDataSource
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
            localDataSource.setStorePassword(storeModel.storePassword)
        }
        return storeModel
    }

    suspend fun setLocalData(storeModel: StoreModel) {
        localDataSource.insertNewStore(storeModel)
        localDataSource.setStoreCode(storeModel.code)
    }

    fun getStoreCode(): String? {
        return localDataSource.getStoreCode()
    }

    suspend fun clearLocalData() {
        localDataSource.clearLocalData()
    }

    fun getHint(theme: ThemeModel, code: String): HintModel? {
        return theme.hintList.find { it.code == code }
    }

    suspend fun getTheme(uid: Int): ThemeModel? {
        val store = localDataSource.getStore()
        return store.themeList.find { it.uid == uid }
    }
}