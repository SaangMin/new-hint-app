package com.skysmyoo.new_hint_app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.skysmyoo.new_hint_app.R
import com.skysmyoo.new_hint_app.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket

class CountDownService : Service() {
    private var remainingSeconds = 10
    private lateinit var udpReceiver: DatagramSocket
    private lateinit var wakeLock: PowerManager.WakeLock
    private lateinit var wifiLock: WifiManager.WifiLock
    private var isListening = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("UDP", "CountDownService 시작됨")

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "UDP::WakeLock")
        wakeLock.acquire()

        // WiFiLock
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "UDP::WifiLock")
        wifiLock.acquire()

        // Foreground 시작 + 서비스 로직 실행
        startForeground(1, createNotification())
        startUDPListener()
        startCountdown()

        return START_STICKY
    }

    private fun startCountdown() {
        CoroutineScope(Dispatchers.IO).launch {
            while (remainingSeconds > 0) {
                delay(1000)
                remainingSeconds--
            }

            vibrate()
        }
    }

    private fun startUDPListener() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                udpReceiver = DatagramSocket(Constants.UDP_RECEIVE_PORT)
                val buffer = ByteArray(1024)
                val packet = DatagramPacket(buffer, buffer.size)

                Log.d("UDP", "UDP Listener 시작")

                while (isListening) {
                    try {
                        udpReceiver.receive(packet)
                        val message = String(packet.data, 0, packet.length)
                        Log.d("UDP", "Received: $message")

                        UDPEventBus.send(message)
//                        vibrate()
                    } catch (e: Exception) {
                        Log.e("UDP", "수신 중 오류 발생: ${e.message}")
                        restartUDPReceiver() // 소켓 복구 시도
                        break
                    }
                }
            } catch (e: Exception) {
                Log.e("UDP", "UDP 소켓 생성 실패: ${e.message}")
            }
        }
    }

    private fun restartUDPReceiver() {
        try {
            isListening = false // 먼저 listening을 끊어줘야 함
            if (::udpReceiver.isInitialized) {
                udpReceiver.close()
            }
        } catch (e: Exception) {
            Log.e("UDP", "UDP 소켓 close 중 오류: ${e.message}")
        }
        isListening = true // 다시 listening 세팅
        startUDPListener()
    }

    private fun vibrate() {
        try {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(effect)
            } else {
                vibrator.vibrate(500) // 500ms 진동
            }
        } catch (e: Exception) {
            Log.e("Vibrate", "진동 실패: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isListening = false
        try {
            if (::udpReceiver.isInitialized) {
                udpReceiver.close()
                Log.d("UDP","udp closed")
            }
        } catch (_: Exception) {}

        if (::wakeLock.isInitialized && wakeLock.isHeld) {
            wakeLock.release()
        }

        if (::wifiLock.isInitialized && wifiLock.isHeld) {
            wifiLock.release()
        }

        Log.d("Service", "CountDownService 정상 종료됨")
    }

    private fun createNotification(): Notification {
        val channelId = "udp_channel_id"
        val channelName = "UDP 통신"

        // 안드로이드 8.0 이상은 알림 채널이 필수
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW // 알림 소리 없음
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("UDP 통신 중")
            .setContentText("서비스가 백그라운드에서 실행 중입니다.")
            .setSmallIcon(R.drawable.call_btn) // 알림 아이콘 (drawable에 있어야 함)
            .setOngoing(true) // 사용자가 제거할 수 없도록
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
