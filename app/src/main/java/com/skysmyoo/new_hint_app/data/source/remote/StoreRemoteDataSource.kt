package com.skysmyoo.new_hint_app.data.source.remote

import android.util.Log
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultError
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultException
import com.skysmyoo.new_hint_app.data.source.remote.response.ApiResultSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
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

    suspend fun sendUDPMessage(message: String, serverIP: String, serverPort: Int): String {
        return withContext(Dispatchers.IO) {
            try {
                val socket = DatagramSocket()
                val address = InetAddress.getByName(serverIP)

                //메시지 전송
                val buffer = message.toByteArray()
                val packet = DatagramPacket(buffer, buffer.size, address, serverPort)
                socket.send(packet)

                //응답 받기
                val responseBuffer = ByteArray(1024)
                val responsePacket = DatagramPacket(responseBuffer, responseBuffer.size)
                socket.receive(responsePacket)

                val response = String(responsePacket.data, 0, responsePacket.length)
                socket.close()
                response
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun startUDPReceiver(
        port: Int,
        onMessageReceived: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val socket = DatagramSocket(port)
                val buffer = ByteArray(1024)
                val packet = DatagramPacket(buffer, buffer.size)

                while (true) {
                    socket.receive(packet)
                    val message = String(packet.data, 0, packet.length)
                    Log.d("StoreRemoteDataSource", "Received message: $message")
                    onMessageReceived(message)
                }
            } catch (e: Exception) {
                onError("Error: ${e.message}")
                Log.d("StoreRemoteDataSource", "Error: ${e.message}")
            }
        }
    }
}