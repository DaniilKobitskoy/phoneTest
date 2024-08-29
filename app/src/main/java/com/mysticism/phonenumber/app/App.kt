package com.mysticism.phonenumber.app

import android.app.Application
import com.mysticism.core.di.coreModule
import com.mysticism.data.di.dataModule
import com.mysticism.domain.di.domainModule
import com.mysticism.favorites.di.favoritesModule
import com.mysticism.phonelist.di.phoneListModule
import com.mysticism.userdetail.di.userDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                phoneListModule,
                dataModule,
                domainModule,
                coreModule,
                favoritesModule,
                userDetailModule
            ))
        }
    }
}