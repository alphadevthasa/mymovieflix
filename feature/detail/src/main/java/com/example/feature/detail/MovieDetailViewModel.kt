package com.example.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import com.example.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val detail: MovieDetail? = null,
    val isLoved: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null,
    val videos: List<Video> = emptyList()
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieRepository
) : ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    val reviews: Flow<PagingData<Review>> = repository.getMovieReviews(movieId)
        .cachedIn(viewModelScope)

    init {
        loadDetail()
        loadVideos()
        loadLovedState()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getMovieDetail(movieId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Gagal memuat detail film"
                    )
                }
                .collect { detail ->
                    _uiState.value = _uiState.value.copy(
                        detail = detail,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    private fun loadLovedState() {
        viewModelScope.launch {
            val loved = repository.isMovieSaved(movieId)
            _uiState.value = _uiState.value.copy(isLoved = loved)
        }
    }

    fun toggleLove(detail: MovieDetail) {
        viewModelScope.launch {
            val nextLoved = !_uiState.value.isLoved

            val movie = com.example.core.domain.model.Movie(
                id = detail.id,
                title = detail.title,
                overview = detail.overview,
                posterPath = detail.posterPath,
                backdropPath = detail.backdropPath,
                releaseDate = detail.releaseDate,
                voteAverage = detail.voteAverage,
                voteCount = detail.voteCount,
                genreIds = detail.genres.map { it.id },
                originalLanguage = detail.originalLanguage,
                popularity = 0.0,
                isAdult = false
            )

            repository.toggleMyList(movie)
            _uiState.value = _uiState.value.copy(isLoved = nextLoved)
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            repository.getMovieVideos(movieId)
                .catch { _ -> }
                .collect { videos ->
                    _uiState.value = _uiState.value.copy(videos = videos)
                }
        }
    }

    fun retry() {
        loadDetail()
        loadVideos()
    }
}