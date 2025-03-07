import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.nfctron.cryptoapp.di.sharedModule  // Make sure you have this module created

class CryptoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoApp)
            modules(sharedModule)
        }
    }
}