package com.example.anggorobenolukito.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.core.utils.Resource
import com.example.anggorobenolukito.domain.model.UserModel

interface IRepository {
    fun getUserSearchResults(query : String) : LiveData<PagingData<UserModel>>
    fun getDetailUser(username: String) : LiveData<Resource<DetailUserEntity>>
    fun setFavouriteUser(data: DetailUserEntity, state: Boolean)
    fun getFavouriteUser(): LiveData<List<DetailUserEntity>>
    fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>>
}