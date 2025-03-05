package com.jiachian.nitra.di

import android.content.Context
import androidx.room.Room
import com.jiachian.cards.data.local.dao.CardDao
import com.jiachian.nitra.data.database.NitraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NitraDatabase {
        return Room.inMemoryDatabaseBuilder(context, NitraDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideCardDao(database: NitraDatabase): CardDao {
        return database.cardDao()
    }
}