package com.bitlasoft.trackingo.koin.module.appModule

import com.bitlasoft.trackingo.domain.repository.CoordinatesRepository
import com.bitlasoft.trackingo.domain.repository.FeedbackRepository
import com.bitlasoft.trackingo.domain.repository.LocationTimeRepository
import org.koin.dsl.module

val RepositoryModule = module {
        single { LocationTimeRepository(get()) }
        single { CoordinatesRepository(get()) }
        single { FeedbackRepository(get()) }
}