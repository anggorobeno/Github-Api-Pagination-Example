package com.example.anggorobenolukito.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.data.remote.response.UserResult
import com.example.anggorobenolukito.utils.Resource

interface IRepository {
    fun getUserSearchResults(query : String) : LiveData<PagingData<UserResult>>
    fun getDetailUser(username: String) : LiveData<Resource<DetailUserEntity>>
    fun setFavouriteUser(data: DetailUserEntity, state: Boolean)
    fun getFavouriteUser(): LiveData<List<DetailUserEntity>>
    fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>>
}