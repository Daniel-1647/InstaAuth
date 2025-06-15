package com.daniel.instaauth.navigation

sealed class Screens(val route: String) {
    data object Auth : Screens("auth_screen")
    data object Home : Screens("home_screen")
}