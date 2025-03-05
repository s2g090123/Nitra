package com.jiachian.cards.domain.usecase

import android.database.sqlite.SQLiteConstraintException
import com.jiachian.cards.data.local.mapper.CardEntityMapper
import com.jiachian.cards.data.repository.CardRepository
import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.util.CardValidator
import com.jiachian.common.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AddCardUseCase @Inject constructor(
    private val repository: CardRepository,
    private val mapper: CardEntityMapper,
    private val cardValidator: CardValidator,
) : CardEntityMapper by mapper {
    operator fun invoke(card: Card): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())

            if (!cardValidator.validateCard(card).isValid) {
                emit(Resource.Error(IllegalArgumentException("The card is invalid.")))
                return@flow
            }

            repository.insertCard(card.toCardEntity())
            emit(Resource.Success(true))
        } catch (e: SQLiteConstraintException) {
            emit(Resource.Error(Exception("An identical card already exists.")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Unknown Error.")))
        }
    }
}