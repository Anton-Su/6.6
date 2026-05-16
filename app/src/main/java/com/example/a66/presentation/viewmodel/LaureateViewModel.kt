package com.example.a66.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.a66.domain.model.NobelPrize
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.a66.domain.usecase.FilterNobelPrizeUseCase
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

    private val _yearText = MutableStateFlow("")
    val yearText: StateFlow<String> = _yearText

    private val _selectedCategory = MutableStateFlow("")
    val selectedCategory: StateFlow<String> = _selectedCategory

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

    fun onYearTextChanged(value: String) {
        _yearText.value = value.filter { it.isDigit() }
    }

    fun onCategoryChanged(value: String) {
        _selectedCategory.value = value
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

    fun applyFilter() {
        val year = _yearText.value.toIntOrNull()
        val category = _selectedCategory.value.ifBlank { null }
        viewModelScope.launch {
            applyFilter(year, category)
        }
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

