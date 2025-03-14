package com.nfctron.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.cryptoapp.repository.CryptocurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CryptoUiState(
    val coins: List<CryptoCurrency> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val searchQuery: String = ""
)

class CryptoViewModel(
    private val repository: CryptocurrencyRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CryptoUiState())
    val uiState: StateFlow<CryptoUiState> = _uiState.asStateFlow()

    init {
        loadTrendingCoins()
    }

    private fun loadTrendingCoins() {
        viewModelScope.launch {
            try {
                val coins = repository.getTrendingCryptocurrencies()
                _uiState.update { it.copy(
                    coins = coins,
                    isLoading = false,
                    error = null
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = e.message
                ) }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        // Implement search logic here
    }

    fun toggleFavorite(coinId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(coinId)
            loadTrendingCoins() // Reload to reflect changes
        }
    }
} 