package com.mysticism.domain.di

import com.mysticism.domain.usecase.FetchAndSaveContactsUseCase
import com.mysticism.domain.usecase.FetchAndSaveImagesUseCase
import com.mysticism.domain.usecase.GetContactsUseCase
import com.mysticism.domain.usecase.GetFavoriteContactsUseCase
import com.mysticism.domain.usecase.UpdateFavoriteStatusUseCase
import org.koin.dsl.module

val domainModule = module {
    single { GetFavoriteContactsUseCase(repository = get()) }
    single { UpdateFavoriteStatusUseCase(repository = get()) }
    single { FetchAndSaveContactsUseCase(get()) }
    single { GetContactsUseCase(get()) }
    single { FetchAndSaveImagesUseCase(get(), get(), get()) }

}