package com.example.anggorobenolukito.data.local.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity

@Database(entities = [DetailUserEntity::class],version = 1,exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}
