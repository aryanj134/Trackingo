package com.bitlasoft.trackingo.koin.module.appModule

import com.bitlasoft.trackingo.viewmodel.FeedbackViewModel
import com.bitlasoft.trackingo.viewmodel.MapTrackingoViewModel
import com.bitlasoft.trackingo.viewmodel.ShortKeyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModule = module {
        viewModel { ShortKeyViewModel(get()) }
        viewModel { MapTrackingoViewModel() }
        viewModel {
                FeedbackViewModel()
        }
}