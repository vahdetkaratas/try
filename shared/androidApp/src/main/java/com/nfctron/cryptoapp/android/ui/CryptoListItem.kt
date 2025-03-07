import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nfctron.cryptoapp.model.CryptoCurrency
import dev.icerock.moko.resources.compose.stringResource

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
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "$ ${crypto.formattedPrice()}",
                            style = MaterialTheme.typography.body1
                        )
                        Text(
                            text = crypto.formattedPriceChange(),
                            color = if (crypto.priceChangePercentage24h >= 0) Color.Green else Color.Red,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
                
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (crypto.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(MR.strings.favorite),
                        tint = if (crypto.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            
            // Price Chart
            crypto.sparklineIn7d?.price?.let { prices ->
                Spacer(modifier = Modifier.height(8.dp))
                PriceChart(
                    prices = prices,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    lineColor = if (crypto.priceChangePercentage24h >= 0) Color.Green else Color.Red
                )
            }
        }
    }
} 