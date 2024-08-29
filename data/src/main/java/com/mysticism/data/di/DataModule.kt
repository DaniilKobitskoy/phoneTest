package com.mysticism.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.mysticism.data.datasource.FileDownloadManager
import com.mysticism.data.local.ContactDatabase
import com.mysticism.data.local.PreferencesManager
import com.mysticism.data.remote.ImageApi
import com.mysticism.data.repository.ContactRepository
import com.mysticism.data.repository.FavoritesContactRepository
import com.mysticism.data.repository.UserIdRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single { Gson() }
    single { androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    single { PreferencesManager(get()) }
    single {
        Room.databaseBuilder(
            get(),
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }
    single { get<ContactDatabase>().contactDao() }
    single { FileDownloadManager(androidContext()) }
    single { ContactRepository(get(), get(), get()) }
    single { FavoritesContactRepository(get()) }
    single { UserIdRepository(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApi::class.java)
    }


}