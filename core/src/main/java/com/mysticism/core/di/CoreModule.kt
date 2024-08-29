package com.mysticism.core.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val coreModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router }
}
