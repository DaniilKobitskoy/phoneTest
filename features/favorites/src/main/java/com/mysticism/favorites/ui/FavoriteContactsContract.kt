package com.mysticism.favorites.ui

import com.mysticism.data.models.Data

interface FavoriteContactsContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showContacts(contacts: List<Data>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        suspend fun fetchContacts()
        suspend fun onFavoriteClicked(contact: Data)
    }
}