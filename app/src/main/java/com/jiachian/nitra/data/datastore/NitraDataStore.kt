package com.jiachian.nitra.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.jiachian.more.data.local.dao.MoreDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

abstract class NitraDataStore {
    abstract val moreDao: MoreDao
}

internal class NitraDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NitraDataStore() {
    private val Context.dataStore by preferencesDataStore(name = "nitra_datastore")

    private object Keys {
        val LOCK = booleanPreferencesKey("lock")
    }

    private val dataStore by lazy {
        context.dataStore
    }

    override val moreDao: MoreDao = object : MoreDao {
        override fun getLockEnabled(): Flow<Boolean> = dataStore.data.map { pref ->
            pref[Keys.LOCK] ?: false
        }

        override suspend fun updateLockEnabled(enabled: Boolean) {
            dataStore.edit { pref ->
                pref[Keys.LOCK] = enabled
            }
        }
    }
}