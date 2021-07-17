package com.example.submission1.core.domain.repository

import com.example.submission1.core.data.Resource
import com.example.submission1.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface IFilmRepository {

    fun getMovies(): Flow<Resource<List<Film>>>
    fun getTV(): Flow<Resource<List<Film>>>

    fun getFavoriteMovies(): Flow<List<Film>>
    fun getFavoriteTV(): Flow<List<Film>>

    fun setFavorite(film: Film, state: Boolean)

}