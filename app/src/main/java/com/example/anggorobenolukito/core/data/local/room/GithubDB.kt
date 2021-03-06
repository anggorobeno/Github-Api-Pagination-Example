package com.example.anggorobenolukito.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.anggorobenolukito.core.data.local.entity.DetailUserEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [DetailUserEntity::class], version = 1, exportSchema = false)
abstract class GithubDB : RoomDatabase() {
    abstract fun dao(): GithubDao

}
