package com.example.anggorobenolukito.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [DetailUserEntity::class],version = 1,exportSchema = false)
abstract class GithubDB : RoomDatabase() {
    abstract fun dao(): GithubDao
    companion object {

        @Volatile
        private var INSTANCE: GithubDB? = null

        fun getInstance(context: Context): GithubDB =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GithubDB::class.java,
                    "Academies.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}
