package com.jiachian.cards.util

import javax.inject.Inject

internal interface ExpiredDateHelper {
    fun yearToString(year: Int): String

    fun yearToInt(year: String): Int

    fun monthToString(month: Int): String

    fun monthToInt(month: String): Int
}

internal class ExpiredDateHelperImpl @Inject constructor() : ExpiredDateHelper {
    override fun yearToString(year: Int): String = year.toString()

    override fun yearToInt(year: String): Int = year.toInt()

    override fun monthToString(month: Int): String = "%02d".format(month)

    override fun monthToInt(month: String): Int = month.toInt()
}