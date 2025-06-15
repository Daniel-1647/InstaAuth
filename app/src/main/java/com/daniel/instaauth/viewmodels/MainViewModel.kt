package com.daniel.instaauth.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.instaauth.data.model.InstagramUser
import com.daniel.instaauth.data.network.RetrofitInstance
import com.daniel.instaauth.datastore.DataStoreManager
import com.daniel.instaauth.util.Constants.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStoreManager = DataStoreManager(application)

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> = _accessToken

    val _isLogout = MutableStateFlow(false)
    val isLogout: StateFlow<Boolean> = _isLogout

    init {
        loadToken()
    }

    private fun loadToken() {
        viewModelScope.launch {
            val token = dataStoreManager.getAccessToken()
            _accessToken.value = token
        }
    }

    fun setAccessToken(token: String) {
        viewModelScope.launch {
            dataStoreManager.saveAccessToken(token)
            _accessToken.value = token
        }
    }

    fun clearAccessToken() {
        viewModelScope.launch {
            dataStoreManager.clearAccessToken()
            _accessToken.value = null
        }
    }

    private val _userProfile = MutableStateFlow<InstagramUser?>(null)
    val userProfile: StateFlow<InstagramUser?> = _userProfile

    fun fetchInstagramUser() {
        viewModelScope.launch {
            val token = _accessToken.value
            if (token.isNullOrBlank()) {
                _userProfile.value = null
                Log.e(TAG, "Token is empty")
                return@launch
            }

            try {
                val user = RetrofitInstance.api.getUserProfile(accessToken = token)
                _userProfile.value = user

            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error: ${e.code()} - ${e.message()}")
                e.response()?.errorBody()?.string()?.let {
                    Log.e(TAG, "Error body: $it")
                }
                _userProfile.value = null
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error: ${e.localizedMessage}")
                _userProfile.value = null
            }
        }
    }

    fun beginLogout(){
        _isLogout.value = true
    }

    fun finishLogout(){
        _isLogout.value = false
    }
}
