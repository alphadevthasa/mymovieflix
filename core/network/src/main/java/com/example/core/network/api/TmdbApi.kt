package com.example.core.network.api

import com.example.core.network.dto.GenreResponse
import com.example.core.network.dto.MovieDetailDto
import com.example.core.network.dto.MovieResponse
import com.example.core.network.dto.ReviewResponse
import com.example.core.network.dto.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetailDto

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): VideoResponse

    @GET("trending/movie/week")
    suspend fun getTrending(): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): MovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getTopRated(
        @Query("sort_by") sortBy: String = "vote_average.desc",
        @Query("vote_count.gte") voteCountGte: Int = 1000,
        @Query("page") page: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse
}