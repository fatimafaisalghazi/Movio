package com.madrid.presentation.viewModel.libraryViewModel.viewAll

interface ViewAllInteractionListener {

    fun onItemClicked(mediaId: Int, mediaType: String)

    fun onItemDeleted(mediaId: Int, mediaType: String)

    fun onRetryClicked()
}