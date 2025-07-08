
package com.hoppswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hoppswap.core.designsystem.theme.AppTheme
import com.hoppswap.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            AppTheme {
                if (!uiState.isLoading) AppNavigation()
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.isLoading }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onIntent(MainIntent.StartApplication)
    }
}