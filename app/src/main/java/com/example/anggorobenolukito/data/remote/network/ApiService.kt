package com.example.anggorobenolukito.data.remote.network

import com.example.anggorobenolukito.BuildConfig
import com.example.anggorobenolukito.data.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("/search/users")
    suspend fun getUserByQuery(
        @Query("page") page: Int,
        @Query("q") query : String,
        @Header("Authorization") apiKey : String
    ): Response<UserResponse>
    @GET("/search/users")
    suspend fun getUserPaged(
        @Query("q") query : String,
        @Query("page") page: Int,
        @Query("per_page") limit : Int,
        @Header("Authorization") apiKey : String = BuildConfig.API_KEY
    ): UserResponse
}