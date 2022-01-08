package com.example.anggorobenolukito.di

import android.content.Context
import com.example.anggorobenolukito.data.local.room.GithubDao
import com.example.anggorobenolukito.data.local.room.DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : DB{
        return DB.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDao(db: DB): GithubDao {
        return db.dao()
    }


}