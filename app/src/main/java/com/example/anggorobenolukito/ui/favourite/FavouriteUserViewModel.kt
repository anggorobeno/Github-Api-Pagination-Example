package com.example.anggorobenolukito.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteUserViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    fun getFavouriteUser(): LiveData<List<DetailUserEntity>> {
        return useCase.getFavouriteUser()
    }

    fun searchFavouriteUser(query: String): LiveData<List<DetailUserEntity>> {
        return useCase.searchFavouriteUser(query)
    }

    fun setFavouriteUser(data: DetailUserEntity) {
        val newState = !data.isFavourite
        useCase.setFavouriteUser(data, newState)

    }
}

