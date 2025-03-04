package com.jiachian.lock.di

import com.jiachian.lock.util.BiometricPromptManager
import com.jiachian.lock.util.BiometricPromptManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BiometricModule {
    @Binds
    @Singleton
    abstract fun bindBiometricPromptManager(impl: BiometricPromptManagerImpl): BiometricPromptManager
}