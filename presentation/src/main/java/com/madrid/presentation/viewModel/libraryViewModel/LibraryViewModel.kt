package com.madrid.presentation.viewModel.libraryViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.shared.MediaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    getWatchListUseCase: GetWatchListsUseCase,
    //getFavoriteUseCase: GetAllFavoriteMoviesUseCase,
    getHistoryUseCase: GetAllMoviesInHistoryUseCase,
) : ViewModel(), LibraryInteractionListener {

    private val _state = MutableStateFlow(LibraryScreenState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LibraryScreenEffect>()
    val effect = _effects.asSharedFlow()


    init {
        getWatchList()
        getFavoriteList()
        getHistoryList()
    }


    private fun getWatchList() {

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onFailure(throwable.message.toString())
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            //val watchList = getWatchListUseCase.invoke().map { it.toWatchListState() }
            //onSuccessGetWatchList(watchList = watchList)
        }
    }

    private fun getFavoriteList() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onFailure(throwable.message.toString())
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            //val favoriteList = getFavoriteUseCase.invoke().map { it.toMediaUiState() }
            //onSuccessGetFavoriteList(favoriteList = favoriteList)
        }
    }

    private fun getHistoryList() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onFailure(throwable.message.toString())
        }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            //val historyList = getHistoryUseCase.invoke().map { it.toMediaUiState() }
            // onSuccessGetHistoryList(historyList = historyList)
        }
    }


    private fun onSuccessGetWatchList(watchList: List<WatchListState>) {
        _state.update {
            it.copy(
                isLoading = false,
                watchList = watchList,
                errorMessage = null
            )
        }
    }

    private fun onSuccessGetFavoriteList(favoriteList: List<MediaUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                favoriteList = favoriteList,
                errorMessage = null
            )
        }
    }

    private fun onSuccessGetHistoryList(historyList: List<MediaUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                historyList = historyList,
                errorMessage = null
            )
        }
    }


    private fun onFailure(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                errorMessage = message
            )
        }
    }


    override fun onItemClick(itemId: Int) { // itemId may be a watchlist item or a media item(favorite or history)
        viewModelScope.launch {
            _effects.emit(LibraryScreenEffect.NavigateToMediaDetails(itemId))
        }
    }

    override fun onViewAllClick(type: String) {  // type = watchlist or favorite or history
        viewModelScope.launch {
            _effects.emit(LibraryScreenEffect.NavigateToViewAll(type))
        }
    }

}