package com.mysticism.domain.usecase

import com.mysticism.data.models.Data
import com.mysticism.data.repository.ContactRepository

class GetFavoriteContactsUseCase(private val repository: ContactRepository) {
    suspend fun execute(): List<Data> {
        return repository.getFavoriteContacts()
    }
}