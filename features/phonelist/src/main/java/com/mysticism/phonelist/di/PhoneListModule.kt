package com.mysticism.phonelist.di

import com.mysticism.phonelist.presenter.ContactListPresenter
import com.mysticism.phonelist.viewModel.ContactListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val phoneListModule = module {
    factory { ContactListPresenter(get(), get(), get()) }
    viewModel { ContactListViewModel(get(), get(), get(), get()) }
}