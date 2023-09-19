package com.skysmyoo.new_hint_app.data.source.remote

import com.google.firebase.database.FirebaseDatabase
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResponse
import com.skysmyoo.new_hint_app.utils.Constants
import javax.inject.Inject

class StoreRemoteDataSource @Inject constructor(private val apiClient: ApiClient) {

    private val storeRef = FirebaseDatabase.getInstance().getReference(Constants.PATH_STORES)

    suspend fun getStoreByCode(code: String): ApiResponse<Map<String, StoreModel?>> {
        return apiClient.getStoreByCode(equalTo = code)
    }
}