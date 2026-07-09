package com.example.feature.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.component.ErrorState
import com.example.core.ui.component.LoadingState
import com.example.core.ui.component.OfflineBanner
import com.example.core.domain.model.Genre

private val genreColors = listOf(
    0xFFE53935, 0xFF1E88E5, 0xFF43A047, 0xFFFB8C00,
    0xFF8E24AA, 0xFF00ACC1, 0xFF3949AB, 0xFFD81B60,
    0xFF6D4C41, 0xFF546E7A, 0xFFF4511E, 0xFF0D47A1,
    0xFF2E7D32, 0xFFAD1457, 0xFF283593, 0xFF00838F,
    0xFF4E342E, 0xFF37474F, 0xFFBF360C, 0xFF1B5E20
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreScreen(
    onGenreClick: (Int, String) -> Unit,
    viewModel: GenreViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "MyMovieFlix",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            OfflineBanner(isConnected = isConnected)
            when {
                state.isLoading && state.genres.isEmpty() -> LoadingState()
                state.error != null && state.genres.isEmpty() -> ErrorState(
                    message = state.error!!,
                    onRetry = { viewModel.loadGenres() }
                )
                else -> GenreGrid(
                    genres = state.genres,
                    onGenreClick = onGenreClick,
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        }
    }
}

@Composable
private fun GenreGrid(
    genres: List<Genre>,
    onGenreClick: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(genres, key = { it.id }) { genre ->
            GenreCard(
                genre = genre,
                color = genreColors[genre.id % genreColors.size],
                onClick = { onGenreClick(genre.id, genre.name) }
            )
        }
    }
}

@Composable
private fun GenreCard(
    genre: Genre,
    color: Long,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(color),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Movie,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier
                        .align(Alignment.End)
                        .height(36.dp)
                )
                Text(
                    text = genre.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}