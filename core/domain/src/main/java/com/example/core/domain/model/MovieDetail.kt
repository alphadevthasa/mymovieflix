package com.example.core.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int?,
    val budget: Long?,
    val revenue: Long?,
    val status: String?,
    val tagline: String?,
    val genres: List<Genre>,
    val originalLanguage: String?,
    val productionCompanies: List<String>,
    val homepage: String?
)