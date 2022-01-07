package com.example.anggorobenolukito.data.remote.response

import com.example.anggorobenolukito.domain.UserModel
import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemsItem>
)

data class ItemsItem(

    @field:SerializedName("repos_url")
    val reposUrl: String ,

    @field:SerializedName("gists_url")
    val gistsUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("starred_url")
    val starredUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @field:SerializedName("score")
    val score: Int,


    @field:SerializedName("avatar_url")
    val avatarUrl: String,


    @field:SerializedName("id")
    val id: Int,


    )
