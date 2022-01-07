package com.example.anggorobenolukito.data.local

import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.data.local.room.Dao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao : Dao) {
    fun insertDetailUser(detailUser : List<DetailUserEntity>) = dao.insertUserDetail(detailUser)
    fun getDetailUser(username : String) = dao.getDetailUser(username)
}