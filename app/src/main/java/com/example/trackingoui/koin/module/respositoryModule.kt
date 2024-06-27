package com.example.trackingoui.koin.module

import com.example.trackingoui.domain.repository.CoordinatesRepository
import com.example.trackingoui.domain.repository.LocationTimeRepository
import org.koin.dsl.module

val repositoryModule = module{
    single { LocationTimeRepository(get()) }
    single { CoordinatesRepository(get()) }
}