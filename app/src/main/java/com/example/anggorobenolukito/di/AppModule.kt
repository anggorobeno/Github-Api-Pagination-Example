package com.example.anggorobenolukito.di

import com.example.anggorobenolukito.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppExecutors() : AppExecutors{
        return AppExecutors()
    }
}