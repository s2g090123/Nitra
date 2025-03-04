package com.jiachian.cards.domain.usecase

import com.jiachian.cards.data.local.mapper.CardEntityMapper
import com.jiachian.cards.data.repository.CardRepository
import com.jiachian.cards.domain.model.Card
import com.jiachian.common.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetCardUseCase @Inject constructor(
    private val repository: CardRepository,
    mapper: CardEntityMapper,
) : CardEntityMapper by mapper {
    operator fun invoke(cardId: Int): Flow<Resource<Card>> = flow {
        try {
            emit(Resource.Loading())
            val card = repository.getCard(cardId)
            emit(Resource.Success(card.toCard()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}