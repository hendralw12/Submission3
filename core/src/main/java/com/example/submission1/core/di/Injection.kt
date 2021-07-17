package com.example.submission1.core.di

import android.content.Context
import com.example.submission1.core.data.source.local.LocalDataSource
import com.example.submission1.core.data.source.local.room.AppDatabase
import com.example.submission1.core.data.source.remote.RemoteDataSource
import com.example.submission1.core.data.source.remote.network.ApiConfig
import com.example.submission1.core.domain.repository.IFilmRepository
import com.example.submission1.core.domain.usecase.FilmInteractor
import com.example.submission1.core.domain.usecase.FilmUseCase
import com.example.submission1.core.utils.AppExecutors

object Injection {
    fun provideMainRepository(context: Context) : IFilmRepository {
        val remoteDataSource : RemoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val database = AppDatabase.getAppDatabase(context)
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()
        return com.example.submission1.core.data.MainRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }

    fun provideFilmUseCase(context: Context): FilmUseCase{
        val repository = provideMainRepository(context)
        return FilmInteractor(repository)
    }
}