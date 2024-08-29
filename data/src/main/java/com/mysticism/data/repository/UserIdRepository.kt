package com.mysticism.data.repository

import com.mysticism.data.local.ContactDao
import com.mysticism.data.models.Data

class UserIdRepository(private val contactDao: ContactDao) {

    suspend fun getUserById(userId: Int): Data? {
        return contactDao.getUserById(userId)
    }
}