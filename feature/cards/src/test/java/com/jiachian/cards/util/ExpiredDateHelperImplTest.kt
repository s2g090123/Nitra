package com.jiachian.cards.util

import org.junit.Assert.*
import org.junit.Test

class ExpiredDateHelperImplTest {
    private val helper = ExpiredDateHelperImpl()

    @Test
    fun `validate expiredDate`() {
        val year = 2030
        val month = 12
        assertEquals(203012, helper.getExpiredDate(year, month))
    }

    @Test
    fun `validate expiredYear`() {
        val expiredDate = 203012
        assertEquals(2030, helper.getExpiredYear(expiredDate))
    }

    @Test
    fun `validate expiredMonth`() {
        val expiredDate = 203012
        assertEquals(12, helper.getExpiredMonth(expiredDate))
    }
}