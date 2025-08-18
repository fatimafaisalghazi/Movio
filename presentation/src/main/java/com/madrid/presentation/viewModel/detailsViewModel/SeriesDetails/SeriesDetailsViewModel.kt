package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Episode
import com.madrid.domain.entity.Review
import com.madrid.domain.entity.Series
import com.madrid.domain.entity.Trailer
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.series.AddRatingSeriesUseCase
import com.madrid.domain.usecase.series.AddSeriesToHistoryUseCase
import com.madrid.domain.usecase.series.GetEpisodeTrailersUseCase
import com.madrid.domain.usecase.series.GetEpisodesForSeasonUseCase
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesReviewsUseCase
import com.madrid.domain.usecase.series.GetSeriesTopCastUseCase
import com.madrid.domain.usecase.series.GetSeriesTrailersUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.domain.usecase.series.IsFavoriteSeriesUseCase
import com.madrid.domain.usecase.series.SetSeriesFavoriteStatusUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.parser.formatDateKotlinx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getSeriesDetailsUseCase: GetSeriesDetailsUseCase,
    val getSeriesTopCastUseCase: GetSeriesTopCastUseCase,
    val getSeriesReviewsUseCase: GetSeriesReviewsUseCase,
    val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
    val getEpisodesForSeasonUseCase: GetEpisodesForSeasonUseCase,
    val addSeriesToHistoryUseCase: AddSeriesToHistoryUseCase,
    val addRatingSeriesUseCase: AddRatingSeriesUseCase,
    val isGuestUseCase: LoginUseCase,
    val getSeriesTrailersUseCase: GetSeriesTrailersUseCase,
    val setSeriesFavoriteStatusUseCase: SetSeriesFavoriteStatusUseCase,
    val isFavoriteSeriesUseCase: IsFavoriteSeriesUseCase,
    val getEpisodeTrailersUseCase: GetEpisodeTrailersUseCase,
) : BaseViewModel<SeriesDetailsUiState
        , SeriesDetailsEffect>(SeriesDetailsUiState()), SeriesDetailsInteractionListener
{
    private val args = savedStateHandle.toRoute<Destinations.SeriesDetailsScreen>()

    init {
        loadData()
        saveSeriesToHistory()
        onClickFavoriteIcon(args.seriesId)
        checkIfFavoriteSeries()
    }

    private fun loadData() {
        loadLoadSeries()
        loadAllSeasonsEpisodes()
        loadCastData()
        loadReviews()
        loadSimilarSeries()
    }

    private fun loadLoadSeries() {
        updateState { it.copy(showLoadingScreen = true) }
        tryToExecute(
            function = { getSeriesDetailsUseCase(args.seriesId) },
            onSuccess = {series-> ::onSuccessLoadSeries.invoke(series) },
            onError = { ::onError.invoke() }
        )
    }

    private fun loadAllSeasonsEpisodes() {
        state.value.currentSeasonsUiStates.forEachIndexed { index, season ->
            tryToExecute(
                function = {
                    getEpisodesForSeasonUseCase(args.seriesId, season.seasonNumber)
                },
                onSuccess = { episodes -> ::onSuccessLoadSeasonsEpisodes.invoke(episodes) },
                onError = { ::onError.invoke() }
            )
        }
    }

    private fun loadCastData() {
        tryToExecute(
            function = { getSeriesTopCastUseCase(args.seriesId) },
            onSuccess = { artists -> ::onSuccessLoadCast.invoke(artists)},
            onError = { ::onError.invoke() }
        )
    }

    private fun loadTrailer() {
        tryToExecute(
            function = { getSeriesTrailersUseCase(args.seriesId) },
            onSuccess = { trailers -> ::onSuccessLoadTrailer.invoke(trailers) },
            onError = { ::onError.invoke() }
        )
    }

    private fun saveSeriesToHistory() {
        viewModelScope.launch { addSeriesToHistoryUseCase(args.seriesId) }
    }

    private fun loadReviews() {
        tryToExecute(
            function = { getSeriesReviewsUseCase(args.seriesId) },
            onSuccess = { reviews -> ::onSuccessLoadReviews.invoke(reviews) },
            onError = { ::onError.invoke() },
        )
    }

    private fun onClickFavoriteIcon(seriesId: Int) {
       tryToExecute(
            function = { setSeriesFavoriteStatusUseCase(seriesId, state.value.isFavourite.not()) },
            onSuccess = { ::onSuccessLoadFavourite.invoke() },
            onError = {::onError.invoke()}
       )
    }

    private fun addRating() {////////////////////////////////////////////////////////////////////////////////////////////
        tryToExecute(
            function = {
               addRatingSeriesUseCase(
                   state.value.seriesId, state.value.userRating.toDouble() * 2
                )
            },
            onSuccess = {},
            onError = {}
        )
    }

    private fun checkIfFavoriteSeries() {
        tryToExecute(
            function = { isFavoriteSeriesUseCase(args.seriesId) },
            onSuccess = { isFavorite -> ::onSuccessCheckIfFavoriteSeries.invoke(isFavorite) },
            onError = { ::onErrorCheckIfFavoriteSeries.invoke() },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = { getSimilarSeriesUseCase(args.seriesId) },
            onSuccess = { allSeries -> ::onSuccessLoadSimilarSeries.invoke(allSeries) },
            onError = { ::onError.invoke() },
        )
    }

    fun onPickRatingNumber(rating: Int) {
        updateState { it.copy(userRating = rating) }
    }

    private fun loadSeasonEpisodes(seasonNumber: Int = 1) {
        tryToExecute(
            function = { getEpisodesForSeasonUseCase(args.seriesId, seasonNumber) },
            onSuccess = { episodes -> ::onSuccessLoadSeasonEpisodes.invoke(episodes,seasonNumber) },
            onError = { ::onError.invoke() },
        )
    }

    private fun onSuccessLoadSeries(series: Series) {
        updateState {
            it.copy(
                seriesId = series.id,
                topImageUrl = series.imageUrl,
                seriesName = series.title,
                seriesGenre = series.genre.map { it.name },
                rate = RateFormatter.formatRate(series.rate),
                numberOfSeasons = series.seasons.size,
                productionDate = formatDateKotlinx(series.airDate),
                description = series.description,
                currentSeasonsUiStates = series.seasons.map { season -> season.mapToUiState() },
                selectedSeasonUiState = series.seasons[if (series.seasons.first().seasonNumber == 0) args.seasonNumber else args.seasonNumber - 1].mapToUiState(),
                showLoadingScreen = false,
                isError = false,
            )
        }
        loadSeasonEpisodes(
            if (series.seasons.first().seasonNumber == 0) args.seasonNumber
            else args.seasonNumber
        )
    }

    fun updateSelectedSeason(seasonNumber: Int) = loadSeasonEpisodes(seasonNumber)

    private fun onSuccessLoadSeasonsEpisodes(episode: List<Episode>) {
        state.value.currentSeasonsUiStates.forEachIndexed { index, season ->
            updateState { currentState ->
                currentState.copy(
                    currentSeasonsUiStates = currentState.currentSeasonsUiStates.
                    mapIndexed { seasonIndex, currentSeason ->
                        if (season.seasonNumber == currentSeason.seasonNumber)
                            currentSeason.copy(
                                numberOfEpisodes = episode.size,
                                episodesUiStates = episode.map { episode -> episode.toUiState()})
                        else
                            currentSeason
                    }
                )
            }
        }
    }

    private fun onSuccessLoadCast(artists:List<Artist>){
        updateState { it.copy(topCast = artists.map { artist -> artist.mapToUiState() })}
    }

    private fun onSuccessLoadReviews(reviews:List<Review>){
        updateState { it.copy(reviews = reviews.map { review -> review.toUiState() })}
    }

    private fun onSuccessLoadTrailer(trailers:List<Trailer>){
        val trailerKey = trailers.firstOrNull()?.key
        if (trailerKey != null) {
            updateState { it.copy(trailerKey = trailerKey) }
        }
    }

    private fun onSuccessLoadFavourite(){
       updateState { it.copy(isFavourite = state.value.isFavourite.not()) }
    }

    private fun onSuccessCheckIfFavoriteSeries(isFavorite:Boolean){
        updateState {
            it.copy(isFavourite = isFavorite)
        }
    }

    private fun onSuccessLoadSimilarSeries(allSeries:List<Series>){
        updateState {
            it.copy(similarSeries = allSeries.map { series ->
                series.toUiState()
            })
        }
    }

    private fun onErrorCheckIfFavoriteSeries(){
        updateState { state ->
            state.copy(isFavourite = false)
        }
    }

    private fun onSuccessLoadSeasonEpisodes(episodes:List<Episode>,seasonNumber:Int){
        updateState { state ->
            state.copy(
                selectedSeasonUiState = state.selectedSeasonUiState.copy(
                    episodesUiStates = episodes.map { episode ->
                        episode.toUiState()
                    },
                    numberOfEpisodes = episodes.size,
                    seasonNumber = seasonNumber,
                    imageUrl = state.currentSeasonsUiStates[
                        if (
                            state.currentSeasonsUiStates.first().seasonNumber == 0) seasonNumber
                        else seasonNumber - 1].imageUrl
                )
            )
        }
    }

    override fun onBackClick() {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateBack)
    }

    override fun onShareClick() {
        TODO("Not yet implemented")
    }

    override fun onRateClick() {
        addRating()
    }

    override fun onFavoriteClick() {
        onClickFavoriteIcon(args.seriesId)
    }

    override fun onAddToListClick() {
        TODO("Not yet implemented")
    }

    override fun onPlayItClick() {
        loadTrailer()
    }

    override fun onSeriesClick() {
        emitNewEffect(effect = SeriesDetailsEffect.NavigateToSeriesDetails(args.seriesId))
    }

    override fun onSeeAllClick() {
        TODO("Not yet implemented")
    }

    override fun onCardClick() {
        TODO("Not yet implemented")
    }

    override fun onRetryClick() {
        //emitNewEffect(effect = SeriesDetailsEffect.RetryLoadData)
        loadData()
    }

    private fun onError() {
        updateState { it.copy(isError = true, showLoadingScreen = false) }
    }
}