package com.madrid.presentation.utils

import android.icu.text.SimpleDateFormat
import kotlinx.datetime.LocalDateTime
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.floor

object RateFormatter {
    fun formatRate(rate: Double): String {
        return try {
            val truncated = floor(rate * 10) / 10
            if (truncated == truncated.toInt().toDouble()) {
                truncated.toInt().toString()
            } else {
                DecimalFormat("#.#").format(truncated)
            }
        } catch (e: Exception) {
            rate.toString()
        }
    }
    fun formatRate(rate: String): String {
        return try {
            val rateValue = rate.toDouble()
            formatRate(rateValue)
        } catch (e: NumberFormatException) {
            rate
        }
    }

    fun formatReviewDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(dateString.take(19))
            outputFormat.format(date)

        } catch (e: Exception) {
            dateString
        }
    }
}