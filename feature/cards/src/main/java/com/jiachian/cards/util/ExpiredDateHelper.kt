package com.jiachian.cards.util

import javax.inject.Inject

internal interface ExpiredDateHelper {
    fun getExpiredDate(expiredYear: Int, expiredMonth: Int): Int

    fun getExpiredYear(expiredDate: Int): Int

    fun getExpiredMonth(expiredDate: Int): Int
}

internal class ExpiredDateHelperImpl @Inject constructor() : ExpiredDateHelper {
    override fun getExpiredDate(expiredYear: Int, expiredMonth: Int): Int {
        return expiredYear * 100 + expiredMonth
    }

    override fun getExpiredYear(expiredDate: Int): Int {
        return expiredDate / 100
    }

    override fun getExpiredMonth(expiredDate: Int): Int {
        return expiredDate % 100
    }

}