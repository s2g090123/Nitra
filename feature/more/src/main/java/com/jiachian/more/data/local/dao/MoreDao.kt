package com.jiachian.more.data.local.dao

import kotlinx.coroutines.flow.Flow

interface MoreDao {
    fun getLockEnabled(): Flow<Boolean>

    suspend fun updateLockEnabled(enabled: Boolean)
}