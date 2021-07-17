package com.example.submission1.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import com.example.submission1.core.domain.model.Film
import com.example.submission1.core.domain.usecase.FilmUseCase

//class MainViewModel(val repository: MainRepository) : ViewModel() {
class MainViewModel(val filmUseCase: FilmUseCase) : ViewModel() {

    val movie = filmUseCase.getAllMovie().asLiveData()

    val tv = filmUseCase.getAllTV().asLiveData()

    val favMovie = filmUseCase.getFavoriteMovie().asLiveData()

    val favTV = filmUseCase.getFavoriteTV().asLiveData()

    fun setFavorite(film: Film, newStatus: Boolean) =
        filmUseCase.setFavorite(film, newStatus)

}