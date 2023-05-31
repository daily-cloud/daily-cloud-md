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
import com.dailycloud.dailycloud.ui.screen.getstarted.GetStartedScreen
import com.dailycloud.dailycloud.ui.screen.history.HistoryScreen
import com.dailycloud.dailycloud.ui.screen.home.HomeScreen
import com.dailycloud.dailycloud.ui.screen.journal.JournalScreen
import com.dailycloud.dailycloud.ui.screen.login.LoginScreen
import com.dailycloud.dailycloud.ui.screen.profile.ProfileScreen
import com.dailycloud.dailycloud.ui.screen.result.ResultScreen
import com.dailycloud.dailycloud.ui.screen.signup.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Screen.GetStarted.route) {
            GetStartedScreen(
                toLogin = {
                    navController.navigate(Screen.Login.route)
                },
                toSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                toHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                },
                toSignUp = {
                    navController.popBackStack()
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                toHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                },
                toLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                toJournal = {
                    navController.navigate(Screen.Journal.route)
                },
                toContent = {
                    navController.navigate(Screen.Content.createRoute(it))
                }
            )
        }
        composable(Screen.Camera.route) {
            CameraScreen(
                toResult = {
                    navController.navigate(Screen.Result.route) {
                        popUpTo(Screen.Journal.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                toJournal = {
                    navController.navigate(Screen.Journal.route)
                }
            )
        }
        composable(Screen.Contents.route) {
            ContentsScreen(
                navigateToContentDetail = {contentId ->
                    navController.navigate(Screen.Content.createRoute(contentId))
                }
            )
        }
        composable(Screen.Result.route) {
            ResultScreen(
                toHome = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                toGetStarted = {
                    navController.navigate(Screen.GetStarted.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Journal.route,) {
            JournalScreen(
                toHome = {
                    navController.navigateUp()
                },
                toCamera = {
                    navController.navigate(Screen.Camera.route)
                },
            )
        }
        composable(
            route = Screen.Content.route,
            arguments = listOf(navArgument("contentId") { type = NavType.StringType } ),
        ) {
            val id = it.arguments?.getString("contentId") ?: ""
            ContentScreen(
                contentId = id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}