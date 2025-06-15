package com.daniel.instaauth.util

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

val TAG = "InstaAuth_Logs"

fun launchUrl(context: Context, url: String) {
    val uri = url.toUri()
    try {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.intent.setPackage("com.android.chrome")
        customTabsIntent.launchUrl(context, uri)
    } catch (e: Exception) {
        // Fallback to default browser
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}