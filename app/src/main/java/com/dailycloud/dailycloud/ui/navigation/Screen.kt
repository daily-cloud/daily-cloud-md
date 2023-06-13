package com.dailycloud.dailycloud.ui.navigation

sealed class Screen(val route: String) {

    object GetStarted : Screen("get-started")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object History : Screen("history")
    object Contents : Screen("contents")
    object Result : Screen("result/{id}") {
        fun createRoute(id: String) = "result/$id"
    }
    object Profile : Screen("profile")

    object EditProfile : Screen("profile/edit/{name}"){
        fun createRoute(name: String) = "profile/edit/$name"
    }

    object Journal : Screen("journal/{journalId}") {
        fun createRoute(journalId: String) = "journal/$journalId"
    }
    object Content : Screen("content/{contentId}") {
        fun createRoute(contentId: String) = "content/$contentId"
    }

}
