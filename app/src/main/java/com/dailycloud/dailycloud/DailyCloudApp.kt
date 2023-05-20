package com.dailycloud.dailycloud

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dailycloud.dailycloud.ui.components.BottomBar
import com.dailycloud.dailycloud.ui.navigation.NavGraph
import com.dailycloud.dailycloud.ui.navigation.Screen

@Composable
fun DailyCloudApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute in listOf(Screen.Home.route, Screen.History.route, Screen.Contents.route)) {
                BottomBar(navController)
            }
        },
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
        )
    }

}