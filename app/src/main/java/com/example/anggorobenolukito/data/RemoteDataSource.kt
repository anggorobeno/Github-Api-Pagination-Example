package com.example.anggorobenolukito.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import com.example.anggorobenolukito.data.remote.network.ApiResponse
import com.example.anggorobenolukito.data.remote.network.ApiService
import com.example.anggorobenolukito.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX: Int = 1

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) :
    PagingSource<Int, ItemsItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsItem> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = apiService.getUserPaged(
                query,
                position,
                params.loadSize
            )
            val result = response.items
            LoadResult.Page(
                data = result,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (result.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    fun getDetailUser(username: String): LiveData<ApiResponse<DetailUserResponse>> {
        val user = MutableLiveData<ApiResponse<DetailUserResponse>>()
        apiService.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    user.value = ApiResponse.success(response.body()!!)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {

            }

        })
        return user

    }

}