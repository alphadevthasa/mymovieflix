package com.example.mymovieflix.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyListUiState(
    val movies: List<Movie> = emptyList()
)

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyListUiState())
    val uiState: StateFlow<MyListUiState> = _uiState.asStateFlow()

    init {
        loadMyList()
    }

    private fun loadMyList() {
        viewModelScope.launch {
            repository.getMyList().collect { movies ->
                _uiState.value = MyListUiState(movies = movies)
            }
        }
    }
}
