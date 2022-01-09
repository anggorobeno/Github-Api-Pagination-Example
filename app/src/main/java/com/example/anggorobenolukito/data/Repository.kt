package com.example.anggorobenolukito.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.anggorobenolukito.data.local.LocalDataSource
import com.example.anggorobenolukito.data.local.LocalDataSource_Factory
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
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

    fun getDetailUser(username: String) =
        object : NetworkBoundResource<DetailUserEntity, DetailUserResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<DetailUserEntity> {
                return localDataSource.getDetailUser(username)
            }

            override fun shouldFetch(data: DetailUserEntity?): Boolean {
                return data == null

            }

            override fun createCall(): LiveData<ApiResponse<DetailUserResponse>> {
                return remoteDataSource.getDetailUser(username)
            }

            override fun saveCallResult(data: DetailUserResponse) {
                val user = DataMapper.mapDetailResponseToEntities(data)
                localDataSource.insertDetailUser(user)
            }
        }.asLiveData()

    fun setFavouriteUser(data: DetailUserEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavouriteUser(data, state) }
    }

    fun getFavouriteUser(): LiveData<List<DetailUserEntity>> {
        return localDataSource.getFavouriteUser()
    }

    fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>> {
        return localDataSource.searchFavouriteUser(query)
    }
}