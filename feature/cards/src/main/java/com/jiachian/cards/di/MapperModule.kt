package com.jiachian.cards.di

import com.jiachian.cards.data.local.mapper.CardEntityMapper
import com.jiachian.cards.data.local.mapper.CardEntityMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MapperModule {
    @Binds
    abstract fun bindCardEntityMapper(impl: CardEntityMapperImpl): CardEntityMapper
}