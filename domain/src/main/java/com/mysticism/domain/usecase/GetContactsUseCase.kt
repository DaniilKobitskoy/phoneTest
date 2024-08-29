package com.mysticism.domain.usecase

import com.mysticism.data.models.Data
import com.mysticism.data.repository.ContactRepository

class GetContactsUseCase(private val repository: ContactRepository) {
    suspend fun execute(): List<Data> {
        return repository.getAllContacts()
    }
}