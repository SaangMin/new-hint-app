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

    fun findTheme(uid: String) {
        viewModelScope.launch {
            val savedThemeModel = repository.getTheme(uid.toInt())
            _savedTheme.value = savedThemeModel
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
}