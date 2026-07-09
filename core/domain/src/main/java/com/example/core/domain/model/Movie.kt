package com.example.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val genreIds: List<Int>,
    val originalLanguage: String?,
    val popularity: Double,
    val isAdult: Boolean
)