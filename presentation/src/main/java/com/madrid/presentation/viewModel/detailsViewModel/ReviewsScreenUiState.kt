package com.madrid.presentation.viewModel.detailsViewModel

import com.madrid.detectimagecontent.R

data class ReviewsScreenUiState(
    val reviews: List<ReviewUiState> = emptyList(),
)

data class ReviewUiState(
    val reviewerName: String,
    val reviewerImageUrl: String?= R.drawable.place_holder.toString(),
    val rating: Float,
    val date: String,
    val content: String
)