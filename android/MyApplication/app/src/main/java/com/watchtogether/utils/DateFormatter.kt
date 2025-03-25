package com.watchtogether.utils

import android.annotation.SuppressLint
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateFormatter {
    @SuppressLint("NewApi")
    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())

    @SuppressLint("NewApi")
    fun formatISODate(isoDate: String): String? {
        return try {
            val instant = Instant.parse(isoDate)
            val dateTime = instant.atZone(ZoneId.systemDefault())
            dateFormatter.format(dateTime)
        } catch (e: Exception) {
            null
        }
    }
} 