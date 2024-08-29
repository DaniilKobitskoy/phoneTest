package com.mysticism.phonelist.ui

import com.mysticism.data.models.Data

interface ContactListContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showContacts(contacts: List<Data>)
        fun showError(message: String)
        fun updateContact(contact: Data)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun fetchContacts()
        fun onFavoriteClicked(contact: Data)
    }
}
