package com.nfctron.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nfctron.shared.domain.models.Cryptocurrency

@Composable
fun CryptoListItem(
    cryptocurrency: Cryptocurrency,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = cryptocurrency.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${cryptocurrency.currentPrice}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${cryptocurrency.priceChangePercentage24h}%",
                    color = if (cryptocurrency.priceChangePercentage24h >= 0) 
                        Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (cryptocurrency.isFavorite) 
                        Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Toggle favorite",
                    tint = if (cryptocurrency.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
} 