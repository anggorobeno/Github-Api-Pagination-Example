package com.example.anggorobenolukito.di

import android.content.Context
import com.example.anggorobenolukito.data.local.room.GithubDao
import com.example.anggorobenolukito.data.local.room.GithubDB
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
    fun provideDatabase(@ApplicationContext context: Context) : GithubDB{
        return GithubDB.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideDao(githubDb: GithubDB): GithubDao {
        return githubDb.dao()
    }


}