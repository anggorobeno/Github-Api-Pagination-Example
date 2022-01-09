package com.example.anggorobenolukito.core.di

import android.content.Context
import androidx.room.Room
import com.example.anggorobenolukito.BuildConfig
import com.example.anggorobenolukito.core.data.Repository
import com.example.anggorobenolukito.core.data.local.room.GithubDB
import com.example.anggorobenolukito.core.data.local.room.GithubDao
import com.example.anggorobenolukito.core.data.remote.network.ApiService
import com.example.anggorobenolukito.domain.repository.IRepository
import com.example.anggorobenolukito.domain.usecase.Interactor
import com.example.anggorobenolukito.domain.usecase.UseCase
import com.example.anggorobenolukito.core.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

    // Database Module
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubDB {
        return Room.databaseBuilder(
            context,
            GithubDB::class.java,
            "Github.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(githubDb: GithubDB): GithubDao {
        return githubDb.dao()
    }

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(repository: Repository): IRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideUseCase(interactor: Interactor): UseCase = interactor
}