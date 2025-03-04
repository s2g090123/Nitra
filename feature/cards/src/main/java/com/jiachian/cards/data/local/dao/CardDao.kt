package com.jiachian.cards.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jiachian.cards.data.local.model.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM table_card")
    fun getCards(): Flow<List<CardEntity>>

    @Query("SELECT * FROM table_card WHERE id=:id")
    suspend fun getCard(id: Int): CardEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity)
}