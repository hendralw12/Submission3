package com.example.submission1.core.domain.usecase

import com.example.submission1.core.data.Resource
import com.example.submission1.core.domain.model.Film
import kotlinx.coroutines.flow.Flow


interface FilmUseCase {
    fun getAllMovie(): Flow<Resource<List<Film>>>
    fun getAllTV(): Flow<Resource<List<Film>>>
    fun getFavoriteMovie(): Flow<List<Film>>
    fun getFavoriteTV(): Flow<List<Film>>
    fun setFavorite(film: Film, state: Boolean)
}