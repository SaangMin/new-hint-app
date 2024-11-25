package com.skysmyoo.new_hint_app.ui.hint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skysmyoo.new_hint_app.data.model.HintModel
import com.skysmyoo.new_hint_app.data.model.ThemeModel
import com.skysmyoo.new_hint_app.data.source.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HintViewModel @Inject constructor(
    private val repository: StoreRepository,
) : ViewModel() {

    private val _isNotFoundHint = MutableStateFlow(false)
    val isNotFoundHint: StateFlow<Boolean> = _isNotFoundHint

    private val _foundedHint = MutableStateFlow<HintModel?>(null)
    val foundedHint: StateFlow<HintModel?> = _foundedHint

    private var _savedTheme = MutableStateFlow<ThemeModel?>(null)
    val savedTheme: StateFlow<ThemeModel?> = _savedTheme

    private val _isShowHint = MutableStateFlow(false)
    val isShowHint: StateFlow<Boolean> = _isShowHint

    private val _isShowProgress = MutableStateFlow(false)
    val isShowProgress: StateFlow<Boolean> = _isShowProgress

    private val _isWifiConnected = MutableStateFlow(false)
    val isWifiConnected: StateFlow<Boolean> = _isWifiConnected

    private val _hintCount = MutableStateFlow<Int>(0)
    val hintCount: StateFlow<Int> = _hintCount

    private val _isShowTranslate = MutableStateFlow(false)
    val isShowTranslate: StateFlow<Boolean> = _isShowTranslate

    private val _isShowCall = MutableStateFlow(false)
    val isShowCall: StateFlow<Boolean> = _isShowCall

    private val _usableCall = MutableStateFlow(false)
    val usableCall: StateFlow<Boolean> = _usableCall

    private val _isShowCallResult = MutableStateFlow(false)
    val isShowCallResult: StateFlow<Boolean> = _isShowCallResult

    private val _isTimeToTranslate = MutableStateFlow(false)
    val isTimeToTranslate: StateFlow<Boolean> = _isTimeToTranslate

    private val _isTimeToCall = MutableStateFlow(false)
    val isTimeToCall: StateFlow<Boolean> = _isTimeToCall

    fun findHint(
        theme: ThemeModel,
        code: String,
    ) {
        viewModelScope.launch {
            val savedHintModel = repository.getHint(theme, code.uppercase())
            if (savedHintModel == null) {
                _isNotFoundHint.value = true
                delay(100)
                _isNotFoundHint.value = false
                return@launch
            } else {
                _foundedHint.value = savedHintModel
                _isShowProgress.value = false
                _isShowHint.value = true
                _hintCount.value++
            }
        }
    }

    fun findProgress(
        theme: ThemeModel,
        code: String,
    ) {
        viewModelScope.launch {
            val savedHintModel = repository.getHint(theme, code.uppercase())
            if (savedHintModel == null) {
                _isNotFoundHint.value = true
                delay(100)
                _isNotFoundHint.value = false
                return@launch
            } else {
                _foundedHint.value = savedHintModel
                _isShowHint.value = false
                _isShowProgress.value = true
            }
        }
    }

    fun findTheme(title: String) {
        viewModelScope.launch {
            val savedThemeModel = repository.getTheme(title)
            _savedTheme.value = savedThemeModel
            _hintCount.value = 0
        }
    }

    fun resetData() {
        _foundedHint.value = null
        _savedTheme.value = null
        _isShowHint.value = false
        _isShowProgress.value = false
    }

    fun closeHint() {
        _isShowHint.value = false
    }

    fun closeProgress() {
        _isShowProgress.value = false
    }

    fun openTranslate() {
        _isShowTranslate.value = true
    }

    fun closeTranslate() {
        _isShowTranslate.value = false
    }

    fun connectWifi() {
        _isWifiConnected.value = !_isWifiConnected.value
    }

    fun executeTranslate() {
        _isTimeToTranslate.value = !_isTimeToTranslate.value
    }

    fun executeCall() {
        _isTimeToCall.value = !_isTimeToCall.value
    }

    fun openCall() {
        _isShowCall.value = true
    }

    fun closeCall() {
        _isShowCall.value = false
    }

    fun setUsableCall() {
        _usableCall.value = !_usableCall.value
    }

    fun openCallResult() {
        _isShowCallResult.value = true
    }

    fun closeCallResult() {
        _isShowCallResult.value = false
    }
}