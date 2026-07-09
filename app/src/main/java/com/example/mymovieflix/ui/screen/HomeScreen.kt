package com.example.mymovieflix.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.component.HeroBanner
import com.example.core.ui.component.MovieCardData
import com.example.core.ui.component.MovieCardNetflix
import com.example.mymovieflix.ui.theme.MyMovieFlixTheme
import com.example.mymovieflix.ui.viewmodel.HomeViewModel

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun HomeScreenPreview() {
    MyMovieFlixTheme {
        HomeScreen(
            onMovieClick = {},
            viewModel = HomeViewModel(FakeMovieRepository())
        )
    }
}

@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    val tabs = listOf("Trending Now", "Now Playing", "Top Rated")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val selectedMovies: List<MovieCardData> = when (selectedTabIndex) {
        0 -> state.trendingMovies
        1 -> state.nowPlayingMovies
        2 -> state.topRatedMovies
        else -> state.trendingMovies
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(bottom = 8.dp)
        ) {
            item {
                if (state.featuredMovie != null) {
                    HeroBanner(
                        backdropUrl = state.featuredMovie!!.backdropPath,
                        title = state.featuredMovie!!.title,
                        description = state.featuredMovie!!.overview,
                        rating = state.featuredMovie!!.voteAverage,
                        onPlayClick = { },
                        onMyListClick = { viewModel.toggleMyList(state.featuredMovie!!) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                TabRow(
                    tabs = tabs,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        fadeIn(tween(300)) + slideInVertically(tween(300)) { height -> height / 2 } togetherWith
                        fadeOut(tween(200)) + slideOutVertically(tween(200)) { height -> -height / 2 }
                    },
                    label = "tab_content"
                ) { tabIndex ->
                    val movies = when (tabIndex) {
                        0 -> state.trendingMovies
                        1 -> state.nowPlayingMovies
                        2 -> state.topRatedMovies
                        else -> state.trendingMovies
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(((movies.size / 3 + 1) * 200).dp.coerceAtMost(600.dp))
                    ) {
                        items(movies, key = { it.id }) { movie ->
                            MovieCardGridItem(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex

            val bgColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF1A1A1A),
                animationSpec = tween(300),
                label = "tab_bg"
            )

            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else Color(0xFF888888),
                animationSpec = tween(300),
                label = "tab_text"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (isSelected) {
                            Modifier.shadow(8.dp, RoundedCornerShape(8.dp), ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        } else {
                            Modifier
                        }
                    )
                    .background(
                        color = bgColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = textColor,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun MovieCardGridItem(
    movie: MovieCardData,
    onClick: () -> Unit
) {
    MovieCardNetflix(
        posterUrl = movie.posterUrl,
        title = movie.title,
        rating = movie.rating,
        onClick = onClick
    )
}
