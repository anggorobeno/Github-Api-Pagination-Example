package com.example.anggorobenolukito.data.local

import androidx.lifecycle.LiveData
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.data.local.room.GithubDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val githubDao : GithubDao) {
    fun insertDetailUser(detailUser : List<DetailUserEntity>) = githubDao.insertUserDetail(detailUser)
    fun getDetailUser(username : String) = githubDao.getDetailUser(username)
    fun setFavouriteUser(data : DetailUserEntity,newState: Boolean){
        data.isFavourite = newState
        githubDao.updateUser(data)
    }
    fun getFavouriteUser() : LiveData<List<DetailUserEntity>>{
        return githubDao.getFavouriteUer()
    }
}