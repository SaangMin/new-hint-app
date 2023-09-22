package com.skysmyoo.new_hint_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: StoreRepository,
) : ViewModel() {
    private val _storeModel = MutableStateFlow<StoreModel?>(null)
    val storeModel: StateFlow<StoreModel?> = _storeModel

    private val _isSuccessFindStore = MutableStateFlow<Boolean>(false)
    val isSuccessFindStore: StateFlow<Boolean> = _isSuccessFindStore

    private val _isSuccessClearLocalData = MutableStateFlow(false)
    val isSuccessClearLocalData: StateFlow<Boolean> = _isSuccessClearLocalData

    fun findStore(code: String) {
        viewModelScope.launch {
            val storeModel = repository.findStore(code)
            if (storeModel != null) {
                _storeModel.value = storeModel
                _isSuccessFindStore.value = true
                delay(100)
                _isSuccessFindStore.value = false
            }
        }
    }

    fun findStoreFromLocal() {
        viewModelScope.launch {
            val storeModel = repository.getStore()
            _storeModel.value = storeModel
        }
    }

    fun getStoreCode(): String? {
        return repository.getStoreCode()
    }

    fun putSample() {
        viewModelScope.launch {
            repository.putSample()
        }
    }

    fun clearLocalData() {
        viewModelScope.launch {
            repository.clearLocalData()
            _isSuccessClearLocalData.value = true
            delay(100)
            _isSuccessClearLocalData.value = false
        }
    }
}