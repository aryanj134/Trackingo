package com.bitlasoft.trackingo.koin.module.appModule

import com.bitlasoft.trackingo.viewModel.CoordinatesViewModel
import com.bitlasoft.trackingo.viewModel.LocationTimeViewModel
import com.bitlasoft.trackingo.viewModel.SharedViewModel
import com.bitlasoft.trackingo.viewModel.FeedbackViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
        viewModel { SharedViewModel() }
        viewModel { LocationTimeViewModel(get()) }
        viewModel { CoordinatesViewModel(get()) }
        viewModel { FeedbackViewModel() }
}