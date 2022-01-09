package com.example.anggorobenolukito.data.remote.network

import com.example.anggorobenolukito.BuildConfig
import com.example.anggorobenolukito.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/search/users")
    suspend fun getUserPaged(
        @Query("q") query : String,
        @Query("page") page: Int,
        @Query("per_page") limit : Int,
        @Header("Authorization") apiKey : String = BuildConfig.API_KEY
    ): UserResponse

    @GET("/users/{username}")
    fun getDetailUser(
        @Path("username") username : String,
        @Header("Authorization") apiKey : String = BuildConfig.API_KEY
    ) : Call<DetailUserResponse>

}