package com.example.submission1.core.utils

import com.example.submission1.core.data.source.local.entity.MovieEntity
import com.example.submission1.core.data.source.local.entity.TVEntity
import com.example.submission1.core.data.source.remote.response.MovieResponse
import com.example.submission1.core.data.source.remote.response.TVResponse
import com.example.submission1.core.domain.model.Film


object DataMapper {

    fun mapResponsesToEntitiesMovies(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id,
                it.overview,
                it.title,
                it.posterPath,
                false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponsesToEntitiesTV(input: List<TVResponse>): List<TVEntity> {
        val movieList = ArrayList<TVEntity>()
        input.map {
            val movie = TVEntity(
                it.id,
                it.overview,
                it.posterPath,
                it.name,
                false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomainMovie(input: List<MovieEntity>): List<Film> =
        input.map {
            Film(
                it.id!!,
                it.title!!,
                it.overview!!,
                "Movie",
                it.posterPath!!,
                it.isFavorite
            )
        }

    fun mapEntitiesToDomainTV(input: List<TVEntity>): List<Film> =
        input.map {
            Film(
                it.id!!,
                it.name!!,
                it.overview!!,
                "TV",
                it.posterPath!!,
                it.isFavorite
            )
        }

    fun mapDomainToEntityMovie(input: Film) = MovieEntity(
        input.id,
        input.desc,
        input.judul,
        input.image,
        input.isFavorite

    )

    fun mapDomainToEntityTV(input: Film) = TVEntity(
        input.id,
        input.desc,
        input.image,
        input.judul,
        input.isFavorite
    )
}