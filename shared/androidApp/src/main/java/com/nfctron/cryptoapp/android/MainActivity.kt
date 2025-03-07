import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.LocalLifecycleOwner
import com.nfctron.cryptoapp.android.theme.CryptoAppTheme
import com.nfctron.cryptoapp.android.navigation.CryptoNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAppTheme {
                CompositionLocalProvider(
                    LocalLifecycleOwner provides this
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        CryptoNavigation()
                    }
                }
            }
        }
    }
} 