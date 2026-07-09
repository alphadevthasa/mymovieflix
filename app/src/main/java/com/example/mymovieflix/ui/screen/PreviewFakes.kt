package com.example.mymovieflix.ui.screen

import androidx.paging.PagingData
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import com.example.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class FakeMovieRepository : MovieRepository {
    override fun getGenres() = kotlinx.coroutines.flow.flowOf<List<Genre>>(emptyList())
    override fun getMoviesByGenre(genreId: Int) = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override suspend fun getMoviesByGenreList(genreId: Int) = emptyList<Movie>()
    override suspend fun searchMoviesList(query: String) = emptyList<Movie>()
    override fun getMovieDetail(movieId: Int) = kotlinx.coroutines.flow.flowOf(
        MovieDetail(
            id = 0,
            title = "",
            overview = "",
            posterPath = null,
            backdropPath = null,
            releaseDate = null,
            voteAverage = 0.0,
            voteCount = 0,
            runtime = null,
            budget = null,
            revenue = null,
            status = null,
            tagline = null,
            genres = emptyList(),
            originalLanguage = null,
            productionCompanies = emptyList(),
            homepage = null
        )
    )
    override fun getMovieReviews(movieId: Int) = kotlinx.coroutines.flow.flowOf(PagingData.empty<Review>())
    override fun getMovieVideos(movieId: Int) = kotlinx.coroutines.flow.flowOf(emptyList<Video>())
    override fun getTrendingMovies() = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override fun getTopRatedMovies() = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override fun getNowPlayingMovies() = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override suspend fun getTrendingMoviesList() = emptyList<Movie>()
    override suspend fun getTopRatedMoviesList() = emptyList<Movie>()
    override suspend fun getNowPlayingMoviesList() = emptyList<Movie>()
    override fun getSimilarMovies(movieId: Int) = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override fun searchMovies(query: String) = kotlinx.coroutines.flow.flowOf(PagingData.empty<Movie>())
    override suspend fun toggleMyList(movie: Movie) {}
    override fun getMyList() = kotlinx.coroutines.flow.flowOf(emptyList<Movie>())
    override suspend fun isMovieSaved(movieId: Int) = false
}
