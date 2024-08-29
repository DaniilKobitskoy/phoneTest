package com.mysticism.domain.usecase

import com.mysticism.data.repository.ContactRepository

class UpdateFavoriteStatusUseCase(private val repository: ContactRepository) {
    suspend fun execute(id: Int, isFavorite: Boolean) {
        repository.updateFavoriteStatus(id, isFavorite)
    }
}