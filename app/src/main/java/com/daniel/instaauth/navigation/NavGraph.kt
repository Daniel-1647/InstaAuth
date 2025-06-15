package com.daniel.instaauth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daniel.instaauth.ui.screens.AuthScreen
import com.daniel.instaauth.ui.screens.HomeScreen
import com.daniel.instaauth.viewmodels.MainViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String, mainViewModel: MainViewModel) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screens.Auth.route) {
            AuthScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }

}