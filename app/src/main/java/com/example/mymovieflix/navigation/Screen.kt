package com.example.mymovieflix.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data class MovieList(val genreId: Int, val genreName: String) :
        Screen("movie_list/{genreId}/{genreName}") {
        fun createRoute(): String = "movie_list/$genreId/${Uri.encode(genreName)}"
        companion object {
            const val ARG_GENRE_ID = "genreId"
            const val ARG_GENRE_NAME = "genreName"
        }
    }
    data class MovieDetail(val movieId: Int) : Screen("movie_detail/{movieId}") {
        fun createRoute(): String = "movie_detail/$movieId"
        companion object {
            const val ARG_MOVIE_ID = "movieId"
        }
    }
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object MyList : Screen("my_list")
    data object Profile : Screen("profile")
}
