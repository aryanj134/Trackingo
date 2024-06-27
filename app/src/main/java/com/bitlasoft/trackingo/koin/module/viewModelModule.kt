package com.bitlasoft.trackingo.koin.module

import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { LocationTimeViewModel(get()) }
    viewModel { CoordinatesViewModel(get()) }
}