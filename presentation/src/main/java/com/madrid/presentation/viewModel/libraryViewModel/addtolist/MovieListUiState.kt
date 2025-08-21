package com.madrid.presentation.viewModel.libraryViewModel.addtolist

import com.madrid.domain.entity.WatchList
import com.madrid.presentation.R


data class MovieListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: Int? = R.string.add_to_list,
    val createListSuccess: Boolean = false,
    val addToListSuccess: Boolean = false,
    val userLists: List<WatchList> = emptyList(),
    val watchListItems: List<WatchListItemUiState> = emptyList(),
    val isLoadingLists: Boolean = false
)

data class WatchListItemUiState(
    val id: Int = 0,
    val videosSize: Int = 0,
    val watchListTitle: String = ""
)

sealed class MovieListEvent {
    object ClearMessages : MovieListEvent()
    object DismissNotification : MovieListEvent()
    object LoadUserLists : MovieListEvent()
}

