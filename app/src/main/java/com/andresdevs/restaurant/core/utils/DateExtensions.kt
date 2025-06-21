package com.andresdevs.restaurant.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(pattern: String = "dd/MM/yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun Long.toDate(): Date {
    return Date(this)
}

fun String.toDate(pattern: String = "dd/MM/yyyy"): Date? {
    return try {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.getHour24(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(this)
}