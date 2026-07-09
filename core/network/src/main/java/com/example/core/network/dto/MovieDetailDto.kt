package com.example.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "budget") val budget: Long?,
    @Json(name = "revenue") val revenue: Long?,
    @Json(name = "status") val status: String?,
    @Json(name = "tagline") val tagline: String?,
    @Json(name = "genres") val genres: List<GenreDto>,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompanyDto>?,
    @Json(name = "homepage") val homepage: String?
)

@JsonClass(generateAdapter = true)
data class ProductionCompanyDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "logo_path") val logoPath: String?,
    @Json(name = "origin_country") val originCountry: String?
)