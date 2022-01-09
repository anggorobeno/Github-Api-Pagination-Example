package com.example.anggorobenolukito.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(entity: List<DetailUserEntity>)

    @Query("SELECT * FROM detail_user_table WHERE username = :username")
    fun getDetailUser(username: String): LiveData<DetailUserEntity>

    @Query("SELECT * FROM detail_user_table WHERE favourite = 1")
    fun getFavouriteUer(): LiveData<List<DetailUserEntity>>

    @Update
    fun updateUser(data: DetailUserEntity)

    @Query("SELECT * FROM detail_user_table WHERE username LIKE '%' || :query || '%' AND favourite = 1")
    fun searchUser(query: String): LiveData<List<DetailUserEntity>>


}