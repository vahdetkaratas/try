package com.nfctron.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.nfctron.app.ui.screens.CryptoListScreen
import com.nfctron.app.ui.theme.NFCtronTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCtronTheme {
                CryptoListScreen()
            }
        }
    }
} 