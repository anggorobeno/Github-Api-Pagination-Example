package com.example.anggorobenolukito.di

import android.content.Context
import androidx.room.Room
import com.example.anggorobenolukito.data.local.room.Dao
import com.example.anggorobenolukito.data.local.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@Singleton
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) {
        Room.databaseBuilder(
            context,
            Database::class.java, "database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDao(db: Database): Dao {
        return db.dao()
    }


}