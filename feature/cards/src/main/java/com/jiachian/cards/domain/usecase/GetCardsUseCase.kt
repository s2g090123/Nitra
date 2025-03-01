package com.jiachian.cards.domain.usecase

import com.jiachian.cards.data.local.mapper.CardEntityMapper
import com.jiachian.cards.data.repository.CardRepository
import com.jiachian.cards.domain.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: CardRepository,
    private val mapper: CardEntityMapper,
) : CardEntityMapper by mapper {
    operator fun invoke(): Flow<List<Card>> {
        return repository
            .getCards()
            .map { list ->
                list.map { it.toCard() }
            }
    }
}