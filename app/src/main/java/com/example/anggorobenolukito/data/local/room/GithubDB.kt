package com.example.anggorobenolukito.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [DetailUserEntity::class], version = 1, exportSchema = false)
abstract class GithubDB : RoomDatabase() {
    abstract fun dao(): GithubDao

}
