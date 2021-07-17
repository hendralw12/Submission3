package com.example.submission1.di

import com.example.submission1.core.domain.usecase.FilmInteractor
import com.example.submission1.core.domain.usecase.FilmUseCase
import com.example.submission1.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module{
    factory<FilmUseCase>{FilmInteractor(get())}
}

val viewModelModule = module{
    viewModel {MainViewModel(get())}

}