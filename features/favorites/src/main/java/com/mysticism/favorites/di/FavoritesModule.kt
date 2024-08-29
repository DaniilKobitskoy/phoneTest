package com.mysticism.favorites.di

import com.mysticism.favorites.presenter.FavoriteContactsPresenter
import org.koin.dsl.module

val favoritesModule = module {
    factory { FavoriteContactsPresenter(get()) }
}