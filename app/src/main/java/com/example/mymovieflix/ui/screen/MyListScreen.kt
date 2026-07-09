package com.example.mymovieflix.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.core.ui.component.BottomNavItem
import com.example.core.ui.component.NetflixBottomNavigation
import com.example.core.ui.component.MovieCardNetflix
import com.example.mymovieflix.ui.theme.MyMovieFlixTheme
import com.example.mymovieflix.ui.viewmodel.MyListViewModel

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MyListScreenPreview() {
    MyMovieFlixTheme {
        MyListScreen(
            onMovieClick = {},
            onNavItemClick = {},
            viewModel = MyListViewModel(FakeMovieRepository())
        )
    }
}

@Composable
fun MyListScreen(
    onMovieClick: (Int) -> Unit,
    onNavItemClick: (BottomNavItem) -> Unit,
    viewModel: MyListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (state.movies.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "My List is empty",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add movies to your list to see them here",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.movies) { movie ->
                    MovieCardNetflix(
                        posterUrl = movie.posterPath,
                        title = movie.title,
                        rating = movie.voteAverage,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }

        NetflixBottomNavigation(
            items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.MyList, BottomNavItem.Profile),
            currentRoute = BottomNavItem.MyList.route,
            onItemClick = onNavItemClick
        )
    }
}
