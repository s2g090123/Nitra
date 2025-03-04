package com.jiachian.cards.domain.usecase

import com.jiachian.cards.data.repository.CardRepository
import javax.inject.Inject

internal class DeleteCardUseCase @Inject constructor(
    private val repository: CardRepository,
) {
    suspend operator fun invoke(id: Int): Boolean {
        return try {
            repository.deleteCard(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}