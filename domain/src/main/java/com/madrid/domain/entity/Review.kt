package com.madrid.domain.entity


import java.util.logging.Logger

data class Review(
    val mediaId: Int = 0,
    val reviewerName: String,
    val userId: String,
    val rate: Double,
    val dateOfRelease: String,
    val comment: String,
) {
    companion object {
        private val logger = Logger.getLogger("Review")  // Java standard logger
    }

    init {
        logger.info("Created Review: $this")
    }

    fun logDetails() {
        logger.info("""
            Review Details:
            Media ID: $mediaId
            Reviewer: $reviewerName
            User ID: $userId
            Rating: $rate
            Date: $dateOfRelease
            Comment: $comment
        """.trimIndent())
    }
}


data class ReviewResult(
    val mediaId: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)