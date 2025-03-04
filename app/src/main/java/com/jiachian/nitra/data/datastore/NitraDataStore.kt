package com.jiachian.nitra.data.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface NitraDataStore {
    val lockEnabled: Flow<Boolean>
}

internal class NitraDataStoreImpl @Inject constructor(
    @ApplicationContext private val application: Application,
) : NitraDataStore {
    private val Context.dataStore by preferencesDataStore(name = "nitra_datastore")

    private object Keys {
        val LOCK = booleanPreferencesKey("lock")
    }

    private val dataStore by lazy {
        application.applicationContext.dataStore
    }

    override val lockEnabled by lazy {
        dataStore.data.map { pref ->
            pref[Keys.LOCK] ?: false
        }
    }
}