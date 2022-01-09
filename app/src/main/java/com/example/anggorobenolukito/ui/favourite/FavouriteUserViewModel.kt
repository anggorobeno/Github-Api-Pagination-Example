package com.example.anggorobenolukito.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.anggorobenolukito.data.Repository
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteUserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getFavouriteUser(): LiveData<List<DetailUserEntity>> {
        return repository.getFavouriteUser()
    }

    fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>> {
        return repository.searchFavouriteUser(query)
    }

    fun setFavouriteUser(data: DetailUserEntity) {
        val newState = !data.isFavourite
        repository.setFavouriteUser(data, newState)

    }
}

