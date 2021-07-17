package com.example.submission1.core.domain.usecase

import com.example.submission1.core.data.Resource
import com.example.submission1.core.domain.model.Film
import com.example.submission1.core.domain.repository.IFilmRepository
import kotlinx.coroutines.flow.Flow


class FilmInteractor(private val filmRepository: IFilmRepository) :
    FilmUseCase {
    override fun getAllMovie(): Flow<Resource<List<Film>>> = filmRepository.getMovies()

    override fun getAllTV(): Flow<Resource<List<Film>>> = filmRepository.getTV()

    override fun getFavoriteMovie(): Flow<List<Film>> = filmRepository.getFavoriteMovies()

    override fun getFavoriteTV(): Flow<List<Film>> = filmRepository.getFavoriteTV()

    override fun setFavorite(film: Film, state: Boolean) =
        filmRepository.setFavorite(film, state)


}