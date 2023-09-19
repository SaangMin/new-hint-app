package com.skysmyoo.new_hint_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skysmyoo.new_hint_app.data.model.StoreModel
import com.skysmyoo.new_hint_app.data.source.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun findStore(code: String) {
        viewModelScope.launch {
            val storeModel = repository.findStore(code)
            _storeModel.value = storeModel
        }
    }

    fun getStore() {
        viewModelScope.launch {
            val storeModel = repository.getStore()
            _storeModel.value = storeModel
        }
    }

    fun getStoreCode(): String? {
        return repository.getStoreCode()
    }
}