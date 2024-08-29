package com.mysticism.favorites.presenter

import com.mysticism.data.models.Data
import com.mysticism.data.repository.FavoritesContactRepository
import com.mysticism.favorites.ui.FavoriteContactsContract

class FavoriteContactsPresenter(
    private val repository: FavoritesContactRepository
) : FavoriteContactsContract.Presenter {

    private var view: FavoriteContactsContract.View? = null

    override fun attachView(view: FavoriteContactsContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override suspend fun fetchContacts() {
        view?.showLoading()
        try {
            val contacts = repository.getFavoriteContacts()
            view?.hideLoading()
            if (contacts.isEmpty()) {
                view?.showError("No favorite contacts found")
            } else {
                view?.showContacts(contacts)
            }
        } catch (e: Exception) {
            view?.hideLoading()
            view?.showError("Error fetching contacts: ${e.message}")
        }
    }

    override suspend fun onFavoriteClicked(contact: Data) {
        val newFavoriteStatus = !contact.isFavorite
        repository.updateFavoriteStatus(contact.id, newFavoriteStatus)
        fetchContacts()
    }
}
