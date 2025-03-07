import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfctron.cryptoapp.model.CryptoCurrency
import com.nfctron.cryptoapp.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoViewModel(
    private val repository: CryptoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CryptoUiState())
    val uiState: StateFlow<CryptoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadTrendingCryptos()
        }
    }

    fun loadTrendingCryptos(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getTrendingCryptos(forceRefresh)
                .onSuccess { cryptos ->
                    _uiState.update { it.copy(
                        cryptos = cryptos,
                        isLoading = false,
                        error = null
                    ) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = error.message
                    ) }
                }
        }
    }

    fun toggleFavorite(cryptoId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(cryptoId, isFavorite)
            loadTrendingCryptos(false)
        }
    }

    fun searchCryptos(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadTrendingCryptos(false)
                return@launch
            }
            
            repository.searchCryptos(query)
                .onSuccess { results ->
                    _uiState.update { it.copy(
                        cryptos = results,
                        error = if (results.isEmpty()) stringResource(MR.strings.no_cryptos_found) else null
                    ) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        error = error.message ?: stringResource(MR.strings.error_generic)
                    ) }
                }
        }
    }
}

data class CryptoUiState(
    val cryptos: List<CryptoCurrency> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 