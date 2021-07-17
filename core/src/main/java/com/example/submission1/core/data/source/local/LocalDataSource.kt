package com.example.submission1.core.data.source.local

import com.example.submission1.core.data.source.local.entity.MovieEntity
import com.example.submission1.core.data.source.local.entity.TVEntity
import com.example.submission1.core.data.source.local.room.FilmDao
import kotlinx.coroutines.flow.Flow

open class LocalDataSource(private val filmDao: FilmDao) {

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(filmDao: FilmDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(filmDao).apply { instance = this }
            }
    }

    fun getAllMovie(): Flow<List<MovieEntity>> = filmDao.getAllMovie()
    fun getAllTV(): Flow<List<TVEntity>> = filmDao.getAllTV()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = filmDao.getFavoriteMovie()
    fun getFavoriteTV(): Flow<List<TVEntity>> = filmDao.getFavoriteTV()

    suspend fun insertMovie(movieList: List<MovieEntity>) = filmDao.insertMovie(movieList)
    suspend fun insertTV(tvList: List<TVEntity>) = filmDao.insertTV(tvList)

     fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        filmDao.UpdateFavMovie(movie)
    }

     fun setFavoriteTV(tv: TVEntity, newState: Boolean) {
        tv.isFavorite = newState
        filmDao.UpdateFavTV(tv)
    }


}