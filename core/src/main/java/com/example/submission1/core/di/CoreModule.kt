package com.example.submission1.core.di

import androidx.room.Room
import com.example.submission1.core.data.source.local.LocalDataSource
import com.example.submission1.core.data.source.local.room.AppDatabase
import com.example.submission1.core.data.source.remote.RemoteDataSource
import com.example.submission1.core.data.source.remote.network.ApiService
import com.example.submission1.core.domain.repository.IFilmRepository
import com.example.submission1.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<AppDatabase>().filmDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "favorite_db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFilmRepository> {
        com.example.submission1.core.data.MainRepository(
            get(),
            get(),
            get()
        )
    }
}
