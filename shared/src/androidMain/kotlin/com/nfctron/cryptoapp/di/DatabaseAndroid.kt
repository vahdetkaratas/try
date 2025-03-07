import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual fun createDatabase(context: Any): CryptoDatabase {
    val driver = AndroidSqliteDriver(
        schema = CryptoDatabase.Schema,
        context = context as Context,
        name = "crypto.db"
    )
    return CryptoDatabase(driver)
} 