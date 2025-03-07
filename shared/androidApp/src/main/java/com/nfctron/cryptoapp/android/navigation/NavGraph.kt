import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object TrendingList : Screen("trending")
    object Favorites : Screen("favorites")
}

@Composable
fun CryptoNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.TrendingList.route) {
        composable(Screen.TrendingList.route) {
            CryptoListScreen(
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) }
            )
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
} 