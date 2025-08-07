package com.madrid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.UserList
import com.madrid.domain.usecase.movie.CreateMovieListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ListUiState {
    object Idle : ListUiState()
    object Loading : ListUiState()
    data class Success(val message: String) : ListUiState()
    data class Error(val message: String) : ListUiState()
}

class MovieListViewModel(
    private val createMovieListUseCase: CreateMovieListUseCase,
    private val getMovieListsUseCase: GetMovieListsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState>(ListUiState.Idle)
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    private val _userLists = MutableStateFlow<List<UserList>>(emptyList())
    val userLists: StateFlow<List<UserList>> = _userLists.asStateFlow()

    fun createMovieList(sessionId: String, name: String) {
        _uiState.value = ListUiState.Loading
        viewModelScope.launch {
            try {
                // Assuming description and language are hardcoded for simplicity
                val status: ListOperationStatus = createMovieListUseCase(sessionId, name, "My new list", "en")
                if (status.success) {
                    _uiState.value = ListUiState.Success(status.message)
                    fetchUserLists(sessionId) // Refresh the list of user lists after a successful creation
                } else {
                    _uiState.value = ListUiState.Error(status.message)
                }
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    // Now this function calls the API via the use case
    fun fetchUserLists(sessionId: String) {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                // Call the use case to get the real lists from the API
                val lists = getMovieListsUseCase(sessionId)
                _userLists.value = lists
                _uiState.value = ListUiState.Idle
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "Failed to fetch movie lists.")
            }
        }
    }

    fun onDismissNotification() {
        _uiState.value = ListUiState.Idle
    }
}