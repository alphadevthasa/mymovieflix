package com.example.feature.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: MovieRepository
) : ViewModel() {

    private val genreId: Int = savedStateHandle.get<Int>("genreId") ?: 0
    private val genreName: String = savedStateHandle.get<String>("genreName") ?: ""

    val genreTitle: String = genreName

    val movies: Flow<PagingData<Movie>> = repository.getMoviesByGenre(genreId)
        .cachedIn(viewModelScope)
}