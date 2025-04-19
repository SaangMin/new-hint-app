package com.skysmyoo.new_hint_app.service

import kotlinx.coroutines.flow.MutableSharedFlow

object UDPEventBus {
    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val messages = _messages

    fun send(message: String) {
        _messages.tryEmit(message) // 안전하게 발송
    }
}
