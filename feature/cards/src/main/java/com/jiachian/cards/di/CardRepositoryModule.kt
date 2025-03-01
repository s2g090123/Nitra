package com.jiachian.cards.di

import com.jiachian.cards.data.repository.CardRepository
import com.jiachian.cards.data.repository.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class CardRepositoryModule {
    @Binds
    abstract fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}