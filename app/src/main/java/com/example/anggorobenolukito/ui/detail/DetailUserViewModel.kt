package com.example.anggorobenolukito.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.domain.usecase.UseCase
import com.example.anggorobenolukito.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val useCase: UseCase) : ViewModel() {
    private val _username = MutableLiveData<String>()

    val getDetail: LiveData<Resource<DetailUserEntity>> =
        Transformations.switchMap(_username) { username ->
            useCase.getDetailUser(username)
        }

    fun setDetailUsername(username: String) {
        _username.value = username
    }

    fun setFavouriteUser() {
        val resource = getDetail.value
        if (resource != null) {
            val detailUser = resource.data
            if (detailUser != null) {
                val newState = !detailUser.isFavourite
                useCase.setFavouriteUser(detailUser, newState)
            }
        }
    }
}