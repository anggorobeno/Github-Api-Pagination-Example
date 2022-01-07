package com.example.anggorobenolukito.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDetail(entity : List<DetailUserEntity>)

    @Query("SELECT * FROM detail_user_table")
    fun getDetailUser(username : String) : LiveData<DetailUserEntity>
}