package com.jiachian.cards.util

import javax.inject.Inject

internal interface ExpiredDateHelper {
    fun getExpiredDate(expiredYear: String, expiredMonth: String): String

    fun getExpiredYear(expiredDate: String): String

    fun getExpiredMonth(expiredDate: String): String
}

internal class ExpiredDateHelperImpl @Inject constructor() : ExpiredDateHelper {
    override fun getExpiredDate(expiredYear: String, expiredMonth: String): String {
        return "$expiredYear$expiredMonth"
    }

    override fun getExpiredYear(expiredDate: String): String {
        return expiredDate.dropLast(2)
    }

    override fun getExpiredMonth(expiredDate: String): String {
        return expiredDate.takeLast(2)
    }

}