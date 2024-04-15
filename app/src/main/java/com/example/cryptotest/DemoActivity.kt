package com.example.cryptotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.example.feature.currencylist.ui.CurrencyListPage

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

//        setContentView(R.layout.activity_main)
        setContent {
            MaterialTheme {
                Surface {
                    CurrencyListPage()
                }
            }
        }
    }
}