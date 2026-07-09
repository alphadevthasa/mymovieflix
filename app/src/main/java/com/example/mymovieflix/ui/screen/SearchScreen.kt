package com.example.mymovieflix.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.component.MovieCardNetflix
import com.example.core.ui.component.NetflixBottomNavigation
import com.example.core.ui.component.BottomNavItem
import com.example.mymovieflix.ui.theme.MyMovieFlixTheme
import com.example.mymovieflix.ui.viewmodel.SearchViewModel

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SearchScreenPreview() {
    MyMovieFlixTheme {
        SearchScreen(
            onMovieClick = {},
            onNavItemClick = {},
            viewModel = SearchViewModel(FakeMovieRepository())
        )
    }
}

@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onNavItemClick: (BottomNavItem) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(
                    text = "Search movies...",
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            singleLine = true,
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.query.isBlank()) {
            GenreChipsGrid(
                genres = state.genres,
                onGenreClick = { genreId, genreName ->
                    viewModel.searchByGenre(genreId)
                }
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.searchResults) { movie ->
                    MovieCardNetflix(
                        posterUrl = movie.posterUrl,
                        title = movie.title,
                        rating = movie.rating,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }

        NetflixBottomNavigation(
            items = listOf(
                BottomNavItem.Home,
                BottomNavItem.Search,
                BottomNavItem.MyList,
                BottomNavItem.Profile
            ),
            currentRoute = BottomNavItem.Search.route,
            onItemClick = onNavItemClick
        )
    }
}

@Composable
private fun GenreChipsGrid(
    genres: List<com.example.core.domain.model.Genre>,
    onGenreClick: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Browse by Genre",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres) { genre ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .clickable(onClick = { onGenreClick(genre.id, genre.name) }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = genre.name,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
