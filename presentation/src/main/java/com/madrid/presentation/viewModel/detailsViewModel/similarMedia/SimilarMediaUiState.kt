package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

data class SimilarMediaUiState(
    val isMovie: Boolean = true,
    val headerName: String = "",
    val medias: List<MediaUiState> = emptyList(),
    val showLoadingScreen: Boolean = false,
    val isError: Boolean =false
)

data class MediaUiState(
    val mediaId: Int = 0,
    val isMovie: Boolean = true,
    val imageUrl: String = "",
    val mediaName: String = "",
    val rate: String = ""
)