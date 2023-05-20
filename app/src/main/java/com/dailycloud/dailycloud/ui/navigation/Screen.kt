package com.dailycloud.dailycloud.ui.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object History : Screen("history")
    object Contents : Screen("contents")
    object Result : Screen("result")
    object Journal : Screen("journal/{journalId}") {
        fun createRoute(journalId: String) = "journal/$journalId"
    }
    object Content : Screen("content/{contentId}") {
        fun createRoute(contentId: String) = "content/$contentId"
    }

}
