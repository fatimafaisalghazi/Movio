package com.madrid.presentation.utils

import com.madrid.domain.entity.Review
import com.madrid.presentation.viewModel.detailsViewModel.ReviewUiState
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.floor

object RateFormatter {

    fun formatRate(rate: Double): String {
        return try {
            val numericRate = rate
            val truncated = floor(numericRate * 10) / 10
            if (truncated == truncated.toInt().toDouble()) {
                truncated.toInt().toString()
            } else {
                DecimalFormat("#.#").format(truncated)
            }
        } catch (e: Exception) {
            rate.toString()
        }
    }

    fun formatDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return ""

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            try {
                val fallbackFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = fallbackFormat.parse(dateString)
                date?.let { outputFormat.format(it) } ?: ""
            } catch (e: Exception) {
                dateString ?: ""
            }
        }
    }

    // Fixed: Convert Review to ReviewUiState instead of returning formatted Review
    fun Review.toReviewUiState(): ReviewUiState {
        return ReviewUiState(
            reviewerName = this.reviewerName,
            reviewerImageUrl = this.reviewerPhotoUrl,
            rating = this.rate.toFloat(), // Convert Double to Float for UI
            date = formatDate(this.date),
            content = this.comment
        )
    }

    fun Review.toFormattedReview(): Review {
        return Review(
            reviewId = this.reviewId,
            reviewerName = this.reviewerName,
            reviewerPhotoUrl = this.reviewerPhotoUrl,
            rate = this.rate,
            date = formatDate(this.date),
            comment = this.comment
        )
    }
}