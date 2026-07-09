package com.example.mymovieflix.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.common.util.Constants
import com.example.core.domain.repository.MovieRepository
import com.example.core.ui.component.MovieCardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val genres: List<Genre> = emptyList(),
    val searchResults: List<MovieCardData> = emptyList(),
    val isLoading: Boolean = false,
    val showResults: Boolean = false
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            repository.getGenres().collect { genres ->
                _uiState.value = _uiState.value.copy(genres = genres)
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
        if (query.length >= 2) {
            searchMovies(query)
        } else {
            _uiState.value = _uiState.value.copy(
                searchResults = emptyList(),
                showResults = false
            )
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, showResults = true)
            val results = repository.searchMoviesList(query)
            _uiState.value = _uiState.value.copy(
                searchResults = results.map { it.mapToCardData() },
                isLoading = false
            )
        }
    }

    fun searchByGenre(genreId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                query = "",
                isLoading = true,
                showResults = true
            )
            val results = repository.getMoviesByGenreList(genreId)
            _uiState.value = _uiState.value.copy(
                searchResults = results.map { it.mapToCardData() },
                isLoading = false
            )
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