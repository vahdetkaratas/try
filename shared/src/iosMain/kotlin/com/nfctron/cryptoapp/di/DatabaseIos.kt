import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual fun createDatabase(context: Any): CryptoDatabase {
    val driver = NativeSqliteDriver(CryptoDatabase.Schema, "crypto.db")
    return CryptoDatabase(driver)
} 