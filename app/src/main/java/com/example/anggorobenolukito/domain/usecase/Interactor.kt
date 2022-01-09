package com.example.anggorobenolukito.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.domain.model.UserModel
import com.example.anggorobenolukito.domain.repository.IRepository
import com.example.anggorobenolukito.core.utils.Resource
import javax.inject.Inject

class Interactor @Inject constructor(private val repository: IRepository) : UseCase {
    override fun getUserSearchResults(query: String): LiveData<PagingData<UserModel>> {
        return repository.getUserSearchResults(query)
    }

    override fun getDetailUser(username: String): LiveData<Resource<DetailUserEntity>> {
        return repository.getDetailUser(username)
    }

    override fun setFavouriteUser(data: DetailUserEntity, state: Boolean) {
        return repository.setFavouriteUser(data, state)
    }

    override fun getFavouriteUser(): LiveData<List<DetailUserEntity>> {
        return repository.getFavouriteUser()
    }

    override fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>> {
        return repository.searchFavouriteUser(query)
    }
}