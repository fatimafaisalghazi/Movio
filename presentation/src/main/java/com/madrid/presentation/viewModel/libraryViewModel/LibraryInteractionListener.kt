package com.madrid.presentation.viewModel.libraryViewModel

interface LibraryInteractionListener {
    fun onItemClick(itemId: Int)
    /* Click on item (watchlistItem,favoriteItem, historyItem)*/

    fun onViewAllClick(type: String)
}