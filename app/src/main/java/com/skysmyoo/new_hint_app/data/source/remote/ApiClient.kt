package com.skysmyoo.new_hint_app.data.source.remote

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("stores.json")
    suspend fun getStoreByCode(
        @Query("orderBy") orderBy: String = "\"code\"",
        @Query("equalTo") equalTo: String,
    ): ApiResponse<Map<String, StoreModel?>>
}