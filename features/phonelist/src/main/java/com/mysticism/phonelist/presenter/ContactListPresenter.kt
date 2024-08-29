package com.mysticism.phonelist.presenter

import com.mysticism.data.models.Data
import com.mysticism.domain.usecase.FetchAndSaveContactsUseCase
import com.mysticism.domain.usecase.GetContactsUseCase
import com.mysticism.domain.usecase.UpdateFavoriteStatusUseCase
import com.mysticism.phonelist.ui.ContactListContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactListPresenter(
    private val fetchAndSaveContactsUseCase: FetchAndSaveContactsUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ContactListContract.Presenter {

    private var view: ContactListContract.View? = null
    private var job: Job? = null

    override fun attachView(view: ContactListContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
        job?.cancel()
    }

    override fun onFavoriteClicked(contact: Data) {
        CoroutineScope(Dispatchers.Main).launch {
            val newFavoriteStatus = !contact.isFavorite
            updateFavoriteStatusUseCase.execute(contact.id, newFavoriteStatus)
            contact.isFavorite = newFavoriteStatus
            view?.updateContact(contact)
        }
    }

    override fun fetchContacts() {
        view?.showLoading()
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                fetchAndSaveContactsUseCase.execute()
                val contacts = getContactsUseCase.execute()
                view?.showContacts(contacts)
            } catch (e: Exception) {
                view?.showError(e.message ?: "Unknown error")
            } finally {
                view?.hideLoading()
            }
        }
    }
}