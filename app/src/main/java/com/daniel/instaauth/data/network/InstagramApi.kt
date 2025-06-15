package com.daniel.instaauth.data.network

import com.daniel.instaauth.data.model.InstagramUser
import retrofit2.http.GET
import retrofit2.http.Query

interface InstagramApi {

    @GET("me")
    suspend fun getUserProfile(
        @Query("fields") fields: String = "user_id,username,name,account_type,profile_picture_url,followers_count,follows_count,media_count",
        @Query("access_token") accessToken: String
    ): InstagramUser
}
