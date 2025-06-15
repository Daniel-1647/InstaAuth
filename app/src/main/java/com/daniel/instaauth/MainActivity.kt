package com.daniel.instaauth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.daniel.instaauth.navigation.Screens
import com.daniel.instaauth.navigation.SetupNavGraph
import com.daniel.instaauth.ui.theme.InstaAuthTheme
import com.daniel.instaauth.util.TAG
import com.daniel.instaauth.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        setContent {
            InstaAuthTheme {
                val navGraph = rememberNavController()
                SetupNavGraph(navGraph, Screens.Auth.route, mainViewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Log.d(TAG, "OnNewIntent: ${intent.dataString}")
        val token = intent.data?.getQueryParameter("token")
        if (token != null) {
            mainViewModel.setAccessToken(token)
        } else {
            Toast.makeText(this, "Null token. Please try to sign in again.", Toast.LENGTH_LONG).show()
        }
        super.onNewIntent(intent)
        setIntent(intent)
    }

}