package com.jiachian.nitra.di

import com.jiachian.more.data.local.dao.MoreDao
import com.jiachian.nitra.data.datastore.NitraDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoreModule {
    @Provides
    fun provideMoreDao(datastore: NitraDataStore): MoreDao {
        return datastore.moreDao
    }
}