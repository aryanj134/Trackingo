package com.bitlasoft.trackingo.koin.module

import com.bitlasoft.trackingo.domain.repository.CoordinatesRepository
import com.bitlasoft.trackingo.domain.repository.LocationTimeRepository
import org.koin.dsl.module

val repositoryModule = module{
    single { LocationTimeRepository(get()) }
    single { CoordinatesRepository(get()) }
}