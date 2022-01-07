package com.example.anggorobenolukito.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.anggorobenolukito.data.local.LocalDataSource
import com.example.anggorobenolukito.data.local.LocalDataSource_Factory
import com.example.anggorobenolukito.data.remote.network.ApiResponse
import com.example.anggorobenolukito.data.remote.network.ApiService
import com.example.anggorobenolukito.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.data.remote.response.ItemsItem
import com.example.anggorobenolukito.domain.DetailUserModel
import com.example.anggorobenolukito.utils.AppExecutors
import com.example.anggorobenolukito.utils.DataMapper
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val apiService: ApiService,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) {
    fun getUserSearchResults(query: String): LiveData<PagingData<ItemsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ), pagingSourceFactory = { RemoteDataSource(apiService, query) }
        ).liveData
    }

    fun getDetailUser(username: String) = object : NetworkBoundResource<DetailUserModel,DetailUserResponse>(appExecutors){
        override fun loadFromDB(): LiveData<DetailUserModel> {
            return localDataSource.getDetailUser(username).map { DataMapper.mapDetailUserEntitiesToModel(it) }
        }

        override fun shouldFetch(data: DetailUserModel?): Boolean {
            return data == null

        }

        override fun createCall(): LiveData<ApiResponse<DetailUserResponse>> {
            return remoteDataSource.getDetailUser(username)
        }

        override fun saveCallResult(data: DetailUserResponse) {
            val user = DataMapper.mapDetailResponseToEntities(data)
            localDataSource.insertDetailUser(user)
        }
    }
}