package com.mysticism.phonelist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysticism.data.models.Data
import com.mysticism.domain.usecase.FetchAndSaveContactsUseCase
import com.mysticism.domain.usecase.FetchAndSaveImagesUseCase
import com.mysticism.domain.usecase.GetContactsUseCase
import com.mysticism.domain.usecase.UpdateFavoriteStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val fetchAndSaveContactsUseCase: FetchAndSaveContactsUseCase,
    private val fetchAndSaveImagesUseCase: FetchAndSaveImagesUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val updateFavoriteStatusUseCase: UpdateFavoriteStatusUseCase
) : ViewModel() {

    private val _contacts = MutableLiveData<List<Data>>()
    val contacts: LiveData<List<Data>> get() = _contacts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchContacts() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchAndSaveContactsUseCase.execute()

                val contactsList = getContactsUseCase.execute()

                if (contactsList.any { it.imagePath == null }) {
                    fetchAndSaveImagesUseCase.execute(contactsList)
                }

                _contacts.postValue(contactsList)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            } finally {
                _loading.postValue(false)
            }
        }
    }


    fun onFavoriteClicked(contact: Data) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newFavoriteStatus = !contact.isFavorite
                updateFavoriteStatusUseCase.execute(contact.id, newFavoriteStatus)
                val updatedContact = contact.copy(isFavorite = newFavoriteStatus)
                _contacts.postValue(_contacts.value?.map {
                    if (it.id == contact.id) updatedContact else it
                })
            } catch (e: Exception) {
                _error.postValue("Error updating favorite status")
            }
        }
    }

}