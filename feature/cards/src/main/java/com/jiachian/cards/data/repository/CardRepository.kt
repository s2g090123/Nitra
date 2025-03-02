package com.jiachian.cards.data.repository

import com.jiachian.cards.data.local.dao.CardDao
import com.jiachian.cards.data.local.model.CardEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal interface CardRepository {
    fun getCards(): Flow<List<CardEntity>>

    suspend fun insertCard(card: CardEntity)

    suspend fun updateCard(card: CardEntity)
}

internal class CardRepositoryImpl @Inject constructor(
    private val dao: CardDao,
) : CardRepository {
    override fun getCards(): Flow<List<CardEntity>> {
        return dao.getCards()
    }

    override suspend fun insertCard(card: CardEntity) {
        dao.insertCard(card)
    }

    override suspend fun updateCard(card: CardEntity) {
        dao.updateCard(card)
    }

}