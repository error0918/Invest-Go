package com.taeyeon.investgo.util

import java.text.DecimalFormat

fun getDigitNumber(number: Int, digits: Int): String {
    return if (digits > 0) {
        if (number.toString().length >= digits) {
            number.toString().substring(0, digits)
        } else {
            "0".repeat(digits - number.toString().length) + number
        }
    } else ""
}

fun formatPrice(price: Int) = DecimalFormat("###,###").format(price)