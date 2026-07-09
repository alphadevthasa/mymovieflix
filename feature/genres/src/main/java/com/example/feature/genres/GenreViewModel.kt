package com.example.feature.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.util.ConnectivityObserver
import com.example.core.domain.model.Genre
import com.example.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    val isConnected: StateFlow<Boolean> = connectivityObserver.isConnected

    init {
        loadGenres()
    }

    fun loadGenres() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                repository.getGenres()
                    .collect { result ->
                        _uiState.value = _uiState.value.copy(
                            genres = result,
                            isLoading = false,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Gagal memuat daftar genre"
                )
            }
        }
    }
}

data class GenreUiState(
    val genres: List<Genre> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)