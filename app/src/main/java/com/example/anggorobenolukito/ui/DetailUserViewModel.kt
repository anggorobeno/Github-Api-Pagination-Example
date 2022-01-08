package com.example.anggorobenolukito.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.anggorobenolukito.data.Repository
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.domain.DetailUserModel
import com.example.anggorobenolukito.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val repository: Repository) : ViewModel(){

    fun getDetailUser(username : String) : LiveData<Resource<DetailUserEntity>>{
        return repository.getDetailUser(username)
    }

}