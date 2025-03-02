package com.jiachian.cards.di

import com.jiachian.cards.util.AESEncryptor
import com.jiachian.cards.util.Encryptor
import com.jiachian.cards.util.ExpiredDateHelper
import com.jiachian.cards.util.ExpiredDateHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UtilModule {
    @Binds
    @Singleton
    abstract fun bindEncryptor(impl: AESEncryptor): Encryptor

    @Binds
    abstract fun bindExpiredDateHelper(impl: ExpiredDateHelperImpl): ExpiredDateHelper
}