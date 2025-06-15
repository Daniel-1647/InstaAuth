package com.daniel.instaauth.ui.screens

import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.daniel.instaauth.navigation.Screens
import com.daniel.instaauth.ui.components.HomeTopAppBar
import com.daniel.instaauth.util.Constants
import com.daniel.instaauth.util.Constants.TAG
import com.daniel.instaauth.viewmodels.MainViewModel

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
    val user = mainViewModel.userProfile.collectAsState()
    val isLogout = mainViewModel.isLogout.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.fetchInstagramUser()
    }

    LaunchedEffect(isLogout.value) {
        if (isLogout.value){
            navController.navigate(Screens.Auth.route) {
                popUpTo(Screens.Home.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar {
                mainViewModel.clearAccessToken()
                mainViewModel.beginLogout()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedCard()
                {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ProfilePicture(
                                url = user.value?.profilePictureUrl.toString(),
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(200.dp)
                            )
                        }
                        Text("Username: ${user.value?.username}")
                        Text("Name: ${user.value?.name}")
                        Text("Account type: ${user.value?.accountType}")
                        Text("User ID: ${user.value?.userId}")
                        Text("Followers: ${user.value?.followersCount}")
                        Text("Following: ${user.value?.followsCount}")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfilePicture(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .listener(
                onError = { _, result ->
                    Log.e(TAG, "Image load failed: ${result.throwable}")
                },
                onSuccess = { _, _ ->
                    Log.d(TAG, "Image load success")
                }
            )
            .build(),

        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}
