import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.koinViewModel
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CryptoListScreen(
    viewModel: CryptoViewModel = koinViewModel(),
    onFavoritesClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { viewModel.loadTrendingCryptos(forceRefresh = true) }
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(MR.strings.app_name)) },
            actions = {
                IconButton(onClick = onFavoritesClick) {
                    Icon(Icons.Default.Favorite, contentDescription = stringResource(MR.strings.favorites))
                }
            }
        )
        
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            when {
                uiState.error != null -> {
                    EmptyState(
                        message = uiState.error!!,
                        icon = Icons.Default.Error
                    )
                }
                uiState.cryptos.isEmpty() && !uiState.isLoading -> {
                    EmptyState(
                        message = stringResource(MR.strings.no_cryptos_found),
                        icon = Icons.Default.SearchOff
                    )
                }
                else -> {
                    Column {
                        SearchBar(
                            onSearch = { query -> viewModel.searchCryptos(query) }
                        )
                        
                        LazyColumn {
                            items(uiState.cryptos) { crypto ->
                                CryptoListItem(
                                    crypto = crypto,
                                    onFavoriteClick = { viewModel.toggleFavorite(crypto.id, !crypto.isFavorite) }
                                )
                            }
                        }
                    }
                }
            }
            
            PullRefreshIndicator(
                refreshing = uiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    
    OutlinedTextField(
        value = text,
        onValueChange = { 
            text = it
            onSearch(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(stringResource(MR.strings.search_hint)) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
    )
}

@Composable
fun CryptoListItem(
    crypto: CryptoCurrency,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = crypto.name,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = crypto.symbol.uppercase(),
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = "$ ${crypto.currentPrice}",
                    style = MaterialTheme.typography.body1
                )
            }
            
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (crypto.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (crypto.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
} 