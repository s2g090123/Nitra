package com.jiachian.cards.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ExpiredDateHelperImplTest {
    private val helper = ExpiredDateHelperImpl()

    @Test
    fun `validate yearToString`() {
        assertEquals("2030", helper.yearToString(2030))
    }

    @Test
    fun `validate yearToInt`() {
        assertEquals(2030, helper.yearToInt("2030"))
    }

    @Test
    fun `validate monthToString`() {
        assertEquals("01", helper.monthToString(1))
    }

    @Test
    fun `validate monthToInt`() {
        assertEquals(1, helper.monthToInt("01"))
    }
}