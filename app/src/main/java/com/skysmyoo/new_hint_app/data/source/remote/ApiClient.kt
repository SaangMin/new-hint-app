package com.skysmyoo.new_hint_app.data.source.remote

import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResponse
import retrofit2.http.GET

interface ApiClient {

    @GET("stores.json")
    suspend fun getStores(): ApiResponse<Map<String, StoreModel?>>

    suspend fun sendUDPMessage(message: String, serverIP: String, serverPort: Int): String

    suspend fun startUDPReceiver(port: Int, onMessageReceived: (String) -> Unit, onError: (String) -> Unit)
}
