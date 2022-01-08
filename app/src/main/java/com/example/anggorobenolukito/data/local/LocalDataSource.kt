package com.example.anggorobenolukito.data.local

import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.data.local.room.GithubDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val githubDao : GithubDao) {
    fun insertDetailUser(detailUser : List<DetailUserEntity>) = githubDao.insertUserDetail(detailUser)
    fun getDetailUser(username : String) = githubDao.getDetailUser(username)
}