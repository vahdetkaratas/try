package com.nfctron.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nfctron.app.ui.components.CryptoListItem
import com.nfctron.app.ui.components.SearchBar
import com.nfctron.app.ui.viewmodel.CryptoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CryptoListScreen(
    viewModel: CryptoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn {
            items(uiState.coins) { coin ->
                CryptoListItem(
                    cryptocurrency = coin,
                    onFavoriteClick = { viewModel.toggleFavorite(coin.id) }
                )
            }
        }
    }
} 