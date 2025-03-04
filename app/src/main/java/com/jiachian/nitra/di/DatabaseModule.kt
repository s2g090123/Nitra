package com.jiachian.nitra.di

import android.content.Context
import androidx.room.Room
import com.jiachian.cards.data.local.dao.CardDao
import com.jiachian.nitra.data.database.NitraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NitraDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = NitraDatabase::class.java,
            name = "nitra_database"
        ).build()
    }

    @Provides
    fun provideCardDao(database: NitraDatabase): CardDao {
        return database.cardDao()
    }
}