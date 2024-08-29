package com.mysticism.userdetail.di

import com.mysticism.userdetail.presenter.UserDetailPresenter
import org.koin.dsl.module

val userDetailModule = module{
    factory { UserDetailPresenter(get(), get(), get()) }
}