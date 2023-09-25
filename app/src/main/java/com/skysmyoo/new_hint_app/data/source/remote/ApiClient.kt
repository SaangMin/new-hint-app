package com.skysmyoo.new_hint_app.data.source.remote

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {

    @POST("stores.json")
    suspend fun putSample(
        @Body store: StoreModel
    ): ApiResponse<Map<String, String>>

    @GET("stores.json")
    suspend fun getStores(): ApiResponse<Map<String, StoreModel?>>
}
