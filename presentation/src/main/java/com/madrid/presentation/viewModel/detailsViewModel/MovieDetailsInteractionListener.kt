package com.madrid.presentation.viewModel.detailsViewModel

interface MovieDetailsInteractionListener {
    fun onClickLoveIcon(movieId: Int)
    fun onPickRatingNumber(rating: Int)
    fun onDismissLoginBottomSheet()
}