package com.example.core.domain.repository

import androidx.paging.PagingData
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getGenres(): Flow<List<Genre>>
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
    suspend fun getMoviesByGenreList(genreId: Int): List<Movie>
    suspend fun searchMoviesList(query: String): List<Movie>
    fun getMovieDetail(movieId: Int): Flow<MovieDetail>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
    fun getMovieVideos(movieId: Int): Flow<List<Video>>
    fun getTrendingMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    suspend fun getTrendingMoviesList(): List<Movie>
    suspend fun getTopRatedMoviesList(): List<Movie>
    suspend fun getNowPlayingMoviesList(): List<Movie>
    fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    suspend fun toggleMyList(movie: Movie)
    fun getMyList(): Flow<List<Movie>>
    suspend fun isMovieSaved(movieId: Int): Boolean
}