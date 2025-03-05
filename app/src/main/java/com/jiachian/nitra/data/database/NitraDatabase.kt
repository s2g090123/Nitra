package com.jiachian.nitra.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jiachian.cards.data.local.dao.CardDao
import com.jiachian.cards.data.local.model.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NitraDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}