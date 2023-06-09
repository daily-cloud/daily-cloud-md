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
import com.dailycloud.dailycloud.ui.screen.profile.EditProfileScreen
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
                    if (it != null) {
                        navController.navigate(Screen.Journal.createRoute(it))
                    } else {
                        navController.navigate(Screen.Journal.route)
                    }
                },
                toContent = {
                    navController.navigate(Screen.Content.createRoute(it))
                }
            )
        }
        composable(Screen.Camera.route) {
            CameraScreen(
                toJournal = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("mood", it)
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                toJournal = {
                    navController.navigate(Screen.Journal.createRoute(it))
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
        composable(
            route = Screen.Result.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType } ),
        ) {
            val id = it.arguments?.getString("id")
            ResultScreen(
                id = id ?: "Error",
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
                },
                toEditProfile = {
                    navController.navigate(Screen.EditProfile.createRoute(it))
                }
            )
        }



        composable(
            route = Screen.Journal.route,
            arguments = listOf(navArgument("journalId") { type = NavType.StringType } ),
        ) {
            val mood = it.savedStateHandle.get<String>("mood")
            val id = it.arguments?.getString("journalId")
            JournalScreen(
                toHome = {
                    navController.navigateUp()
                },
                toCamera = {
                    navController.navigate(Screen.Camera.route)
                },
                toResult = { result ->
                    navController.navigate(Screen.Result.createRoute(result.journalId!!)) {
                        popUpTo(Screen.Journal.route) { inclusive = true }
                    }
                },
                mood = mood,
                journalId = id
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

        composable(
            route = Screen.EditProfile.route) {
            val name = it.arguments?.getString("name") ?: ""
            EditProfileScreen(
                backToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                name = name
            )
        }
    }
}