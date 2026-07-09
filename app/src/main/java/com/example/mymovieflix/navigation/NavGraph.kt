package com.example.mymovieflix.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature.detail.MovieDetailScreen
import com.example.feature.movies.MovieListScreen
import com.example.mymovieflix.ui.screen.HomeScreen
import com.example.mymovieflix.ui.screen.MyListScreen
import com.example.mymovieflix.ui.screen.ProfileScreen
import com.example.mymovieflix.ui.screen.SearchScreen
import com.example.mymovieflix.ui.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Splash.route, modifier = modifier) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId).createRoute())
                }
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId).createRoute())
                }
            )
        }
        composable(Screen.MyList.route) {
            MyListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId).createRoute())
                }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = Screen.MovieList(0, "").route,
            arguments = listOf(
                navArgument(Screen.MovieList.ARG_GENRE_ID) { type = NavType.IntType },
                navArgument(Screen.MovieList.ARG_GENRE_NAME) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val genreId = backStackEntry.arguments?.getInt(Screen.MovieList.ARG_GENRE_ID) ?: 0
            val genreName = backStackEntry.arguments?.getString(Screen.MovieList.ARG_GENRE_NAME) ?: ""
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId).createRoute())
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.MovieDetail(0).route,
            arguments = listOf(
                navArgument(Screen.MovieDetail.ARG_MOVIE_ID) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(Screen.MovieDetail.ARG_MOVIE_ID) ?: 0
            MovieDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}