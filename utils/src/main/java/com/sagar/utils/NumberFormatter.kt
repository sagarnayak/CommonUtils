package com.sagar.utils

import android.text.TextUtils
import java.util.*

class NumberFormatter(
    private val locale: Locale = Locale.getDefault(),
    private val appUtil: AppUtil
) {
    private var separator = ""
    private var decimalSeparator = ""

    init {
        calculateSymbols()
    }

    fun getFormattedString(number: String): String {
        return appUtil.formatNumber(number, locale)
    }

    fun getRawNumber(formattedString: String): String {
        var toReturn = formattedString.replace(separator, "")
        toReturn = toReturn.replace(decimalSeparator, ".")
        return toReturn
    }

    private fun calculateSymbols() {
        val numberToTest = 1000000.45
        val formattedNumber = appUtil.formatNumber(numberToTest, locale)
        val chars = formattedNumber.toCharArray()
        decimalSeparator = chars[(formattedNumber.length - 1) - 2].toString()
        chars.find {
            !TextUtils.isDigitsOnly(it.toString())
        }?.let {
            separator = it.toString()
        }
    }
}