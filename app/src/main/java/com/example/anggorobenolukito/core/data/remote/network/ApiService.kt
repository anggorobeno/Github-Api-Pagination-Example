package com.example.anggorobenolukito.core.data.remote.network

import com.example.anggorobenolukito.BuildConfig
import com.example.anggorobenolukito.core.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.core.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    suspend fun getUserPaged(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
    ): UserResponse

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<DetailUserResponse>

}