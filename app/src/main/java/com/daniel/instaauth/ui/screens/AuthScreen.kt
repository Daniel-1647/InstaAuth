package com.daniel.instaauth.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.daniel.instaauth.navigation.Screens
import com.daniel.instaauth.util.Constants
import com.daniel.instaauth.util.launchUrl
import com.daniel.instaauth.viewmodels.MainViewModel

@Composable
fun AuthScreen(navController: NavHostController, modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val context = LocalContext.current

    val accessToken = mainViewModel.accessToken.collectAsState()

    LaunchedEffect(accessToken.value) {
        if (!accessToken.value.isNullOrBlank()){
            navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Auth.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.finishLogout() //Reset var
    }

    Scaffold {innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                AuthContent(
                    onLogin = {
                        launchUrl(context, Constants.INSTAGRAM_EMBED_URL)
                    },
                    onLogToken = {
                        Log.d(Constants.TAG, mainViewModel.accessToken.value ?: "null token")
                    }
                )
            }
        }
    }
}

@Composable
fun AuthContent(onLogin: () -> Unit, onLogToken: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp),
        onClick = onLogin
    ) {
        Text("Login via Instagram")
    }
    Button(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp),
        onClick = onLogToken
    ) {
        Text("Log Token")
    }
}