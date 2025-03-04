package com.jiachian.cards.di

import com.jiachian.cards.data.local.mapper.CardEntityMapper
import com.jiachian.cards.data.local.mapper.CardEntityMapperImpl
import com.jiachian.cards.domain.mapper.CardMapper
import com.jiachian.cards.domain.mapper.CardMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class MapperModule {
    @Binds
    abstract fun bindCardEntityMapper(impl: CardEntityMapperImpl): CardEntityMapper

    @Binds
    abstract fun bindCardMapper(impl: CardMapperImpl): CardMapper
}