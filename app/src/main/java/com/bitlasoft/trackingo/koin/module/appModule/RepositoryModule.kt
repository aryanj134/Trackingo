package com.bitlasoft.trackingo.koin.module.appModule

import com.bitlasoft.trackingo.domain.repository.ShortKeyRepository
import org.koin.dsl.module

val RepositoryModule = module {
        single { ShortKeyRepository(get()) }
    }