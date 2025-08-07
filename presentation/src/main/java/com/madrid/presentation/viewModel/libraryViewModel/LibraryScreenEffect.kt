package com.madrid.presentation.viewModel.libraryViewModel

sealed class LibraryScreenEffect {


    data class NavigateToMediaDetails(val mediaId: Int) :
        LibraryScreenEffect() // favorite or history item click

    data class NavigateToWatchListDetails(val watchListId: Int) :
        LibraryScreenEffect() // watchlist item click


    // navigate to view all
    // type = (watchlist or favorite or history)
    data class NavigateToViewAll(val type: String) :
        LibraryScreenEffect()

}

