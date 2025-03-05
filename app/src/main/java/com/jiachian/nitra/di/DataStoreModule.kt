package com.jiachian.nitra.di

import com.jiachian.nitra.data.datastore.NitraDataStore
import com.jiachian.nitra.data.datastore.NitraDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataStoreModule {
    @Binds
    @Singleton
    abstract fun bindNitraDataStore(impl: NitraDataStoreImpl): NitraDataStore
}