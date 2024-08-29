package com.mysticism.domain.usecase

import com.mysticism.data.repository.ContactRepository

class FetchAndSaveContactsUseCase(private val repository: ContactRepository) {
    suspend fun execute() {
        repository.fetchAndSaveContactsIfNeeded()
    }
}