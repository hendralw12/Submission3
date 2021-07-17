package com.example.submission1.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val FavoriteModule = module {
    viewModel { MainViewModel(get()) }
}