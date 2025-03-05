package com.jiachian.more.domain.usecase

import com.jiachian.more.data.local.dao.MoreDao
import javax.inject.Inject

class GetLockEnabledUseCase @Inject constructor(
    private val dao: MoreDao,
) {
    operator fun invoke() = dao.getLockEnabled()
}