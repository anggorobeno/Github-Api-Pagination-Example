package com.example.anggorobenolukito.core.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.anggorobenolukito.core.data.local.LocalDataSource
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.core.data.remote.RemoteDataSource
import com.example.anggorobenolukito.core.data.remote.network.ApiResponse
import com.example.anggorobenolukito.core.data.remote.network.ApiService
import com.example.anggorobenolukito.core.data.remote.response.DetailUserResponse
import com.example.anggorobenolukito.core.utils.AppExecutors
import com.example.anggorobenolukito.core.utils.DataMapper
import com.example.anggorobenolukito.domain.model.UserModel
import com.example.anggorobenolukito.domain.repository.IRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val apiService: ApiService,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IRepository {
    override fun getUserSearchResults(query: String): LiveData<PagingData<UserModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false,
            ), pagingSourceFactory = { RemoteDataSource(apiService, query) }
        ).liveData
    }

    override fun getDetailUser(username: String) =
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

    override fun setFavouriteUser(data: DetailUserEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavouriteUser(data, state) }
    }

    override fun getFavouriteUser(): LiveData<List<DetailUserEntity>> {
        return localDataSource.getFavouriteUser()
    }

    override fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>> {
        return localDataSource.searchFavouriteUser(query)
    }
}