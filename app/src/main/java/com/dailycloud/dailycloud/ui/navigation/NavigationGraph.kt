package com.dailycloud.dailycloud.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dailycloud.dailycloud.ui.screen.camera.CameraScreen
import com.dailycloud.dailycloud.ui.screen.content.ContentScreen
import com.dailycloud.dailycloud.ui.screen.contents.ContentsScreen
import com.dailycloud.dailycloud.ui.screen.history.HistoryScreen
import com.dailycloud.dailycloud.ui.screen.home.HomeScreen
import com.dailycloud.dailycloud.ui.screen.journal.JournalScreen
import com.dailycloud.dailycloud.ui.screen.login.LoginScreen
import com.dailycloud.dailycloud.ui.screen.result.ResultScreen
import com.dailycloud.dailycloud.ui.screen.signup.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(Screen.Login.route) {
            LoginScreen()
        }
        composable(Screen.SignUp.route) {
            SignUpScreen()
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Camera.route) {
            CameraScreen()
        }
        composable(Screen.History.route) {
            HistoryScreen()
        }
        composable(Screen.Contents.route) {
            ContentsScreen()
        }
        composable(Screen.Result.route) {
            ResultScreen()
        }
        composable(
            route = Screen.Journal.route,
            arguments = listOf(navArgument("journalId") { type = NavType.StringType } ),
        ) {
            val id = it.arguments?.getString("journalId") ?: ""
            JournalScreen()
        }
        composable(
            route = Screen.Content.route,
            arguments = listOf(navArgument("contentId") { type = NavType.StringType } ),
        ) {
            val id = it.arguments?.getString("contentId") ?: ""
            ContentScreen()
        }
    }
}