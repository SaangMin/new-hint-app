package com.skysmyoo.new_hint_app.data.source.remote

import android.util.Log
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultError
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultException
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultSuccess
import javax.inject.Inject

class StoreRemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getStoreByCode(code: String): StoreModel? {
        return when (val response = apiClient.getStores()) {
            is ApiResultSuccess -> {
                for (store in response.data.values) {
                    if (store?.code == code) {
                        return store
                    }
                }
                null
            }

            is ApiResultError -> {
                Log.e("StoreRemoteDataSource", "response Error: ${response.message}")
                null
            }

            is ApiResultException -> {
                Log.e("StoreRemoteDataSource", "response Exception : ${response.throwable}")
                null
            }
        }
    }
}