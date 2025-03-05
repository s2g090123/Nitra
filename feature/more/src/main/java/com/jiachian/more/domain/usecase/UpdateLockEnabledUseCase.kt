package com.jiachian.more.domain.usecase

import com.jiachian.more.data.local.dao.MoreDao
import javax.inject.Inject

class UpdateLockEnabledUseCase @Inject constructor(
    private val dao: MoreDao
) {
    suspend operator fun invoke(enabled: Boolean) {
        dao.updateLockEnabled(enabled)
    }
}