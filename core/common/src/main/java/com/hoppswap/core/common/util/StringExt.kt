package com.hoppswap.core.common.util

fun String.isValidEmail(): Boolean {
    return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(this)
}

fun String.toInitials(maxChars: Int = 2): String {
    return this
        .trim()
        .split("\\s+".toRegex()) // split by any whitespace
        .mapNotNull { it.firstOrNull()?.uppercaseChar() } // get uppercase initial
        .let {
            if (it.size == 1) it.first().toString()
            else "${it.first()}${it.last()}"
        }
        .take(maxChars)
}