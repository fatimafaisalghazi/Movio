package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails


interface SeriesDetailsInteractionListener {
    fun onBackClick()
    fun onRateClick()
    fun onFavoriteClick(seriesId: Int)
    fun onPlayItClick()
    fun onEpisodePlayItClick(
        seriesId: Int, seasonNumber: Int
        ,episodeNumber: Int, onTrailerLoaded: (String?) -> Unit
    )
    fun onSeriesClick()
    fun onSeeAllClick(seriesId: Int, seeAllType:SeeAllType)
    fun onActorCardClick(actorId:Int)
    fun onSimilarSeriesCardClick(seriesId: Int)
    fun onCurrentSeasonCardClick(seriesId: Int,seasonNumber:Int)
    fun onRetryClick()
    fun onLoginClick()
    fun onShareShareBottomSheetClick()
    fun onDismissShareShareBottomSheetClick()
    fun onShowAddRatingBottomSheetClick()
    fun onShowDoneRatingBottomSheetClick()
    fun onDismissShowDoneRatingBottomSheetClick()
    fun onDismissAddRatingBottomSheet()
    fun onShowSnackBar()
    fun onDismissSnackBar()
}