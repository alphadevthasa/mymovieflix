package com.example.mymovieflix.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.MovieRepository
import com.example.core.common.util.Constants
import com.example.core.ui.component.MovieCardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val featuredMovie: Movie? = null,
    val trendingMovies: List<MovieCardData> = emptyList(),
    val topRatedMovies: List<MovieCardData> = emptyList(),
    val nowPlayingMovies: List<MovieCardData> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            val trending = repository.getTrendingMoviesList()
            _uiState.value = _uiState.value.copy(
                trendingMovies = trending.map { it.mapToCardData() },
                featuredMovie = trending.firstOrNull()
            )
        }
        viewModelScope.launch {
            val topRated = repository.getTopRatedMoviesList()
            _uiState.value = _uiState.value.copy(
                topRatedMovies = topRated.map { it.mapToCardData() }
            )
        }
        viewModelScope.launch {
            val nowPlaying = repository.getNowPlayingMoviesList()
            _uiState.value = _uiState.value.copy(
                nowPlayingMovies = nowPlaying.map { it.mapToCardData() }
            )
        }
    }

    fun toggleMyList(movie: Movie) {
        viewModelScope.launch {
            repository.toggleMyList(movie)
        }
    }

    private fun Movie.mapToCardData(): MovieCardData {
        val path = posterPath
        val posterUrl = when {
            path == null -> null
            path.startsWith("/") -> "${Constants.IMAGE_BASE_URL}$path"
            path.startsWith("http") -> path
            else -> path
        }

        return MovieCardData(
            id = id,
            posterUrl = posterUrl,
            title = title,
            rating = voteAverage
        )
    }
}
