@Composable
fun FavoritesScreen(
    viewModel: CryptoViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val favorites = uiState.cryptos.filter { it.isFavorite }
    
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(MR.strings.favorites)) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        
        LazyColumn {
            items(favorites) { crypto ->
                CryptoListItem(
                    crypto = crypto,
                    onFavoriteClick = { viewModel.toggleFavorite(crypto.id, !crypto.isFavorite) }
                )
            }
        }
    }
} 