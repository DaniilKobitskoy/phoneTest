package com.mysticism.data.repository

import com.mysticism.data.local.ContactDao
import com.mysticism.data.models.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesContactRepository(
    private val contactDao: ContactDao
) {

    suspend fun getFavoriteContacts(): List<Data> {
        return withContext(Dispatchers.IO) {
            contactDao.getFavoriteContacts()
        }
    }

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        withContext(Dispatchers.IO) {
            contactDao.updateFavoriteStatus(id, isFavorite)
        }
    }

}