package com.daniel.instaauth.data.model

import com.google.gson.annotations.SerializedName

data class InstagramUser(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("account_type")
    val accountType: String,

    @SerializedName("profile_picture_url")
    val profilePictureUrl: String,

    @SerializedName("followers_count")
    val followersCount: Int,

    @SerializedName("follows_count")
    val followsCount: Int,

    @SerializedName("media_count")
    val mediaCount: Int
)