package com.jiachian.cards.di

import com.jiachian.cards.data.repository.CardRepository
import com.jiachian.cards.data.repository.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CardRepositoryModule {
    @Binds
    abstract fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}