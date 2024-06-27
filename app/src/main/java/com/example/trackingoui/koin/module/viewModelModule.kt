package com.example.trackingoui.koin.module

import com.example.trackingoui.viewModel.CoordinatesViewModel
import com.example.trackingoui.viewModel.LocationTimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { LocationTimeViewModel(get()) }
    viewModel { CoordinatesViewModel(get()) }
}