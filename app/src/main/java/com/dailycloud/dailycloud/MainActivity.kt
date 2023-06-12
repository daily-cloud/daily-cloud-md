package com.dailycloud.dailycloud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepVisibleCondition { viewModel.isLoading.value }

        setContent {
            DailyCloudTheme(darkTheme = false) {
                val startDestination by viewModel.startDestination
                DailyCloudApp(startDestination = startDestination)
            }
        }
    }
}