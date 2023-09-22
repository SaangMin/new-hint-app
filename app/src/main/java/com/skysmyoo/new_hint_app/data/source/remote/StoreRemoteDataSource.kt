package com.skysmyoo.new_hint_app.data.source.remote

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResponse
import javax.inject.Inject

class StoreRemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    suspend fun getStoreByCode(code: String): StoreModel? {
        val response = apiClient.getStores()

        for (store in response.values) {
            if (store?.code == code) {
                return store
            }
        }
        return null
    }

    suspend fun putSample(storeModel: StoreModel): ApiResponse<Map<String, String>> {
        return apiClient.putSample(storeModel)
    }
}