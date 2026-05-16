package com.example.a62.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.a62.domain.model.NobelPrize
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.a62.domain.usecase.FilterNobelPrizeUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}


class LaureateViewModel(val filterUseCase: FilterNobelPrizeUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<NobelPrize>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<NobelPrize>>> = _uiState
    val laureates: StateFlow<List<NobelPrize>> = uiState
        .map { state ->
            when (state) {
                is UiState.Success -> state.data
                else -> emptyList()
            }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        fetchLaureates()
    }

    private fun fetchLaureates() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val laureats = filterUseCase(null, null)
                _uiState.value = UiState.Success(laureats)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun retry() {
        fetchLaureates()
    }


    suspend fun applyFilter(nobelPrizeYear: Int?, nobelPrizeCategory: String?) {
        _uiState.value = UiState.Loading
        try {
            val list = filterUseCase(nobelPrizeYear, nobelPrizeCategory)
            _uiState.value = UiState.Success(list)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Unknown Error")
        }
    }
}
