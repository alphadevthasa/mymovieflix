package com.example.core.network.dto

import com.example.core.common.util.Constants
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video

fun GenreDto.toDomain() = Genre(id = id, name = name)

fun MovieDto.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
    backdropPath = backdropPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    popularity = popularity,
    isAdult = isAdult
)

fun MovieDetailDto.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
    backdropPath = backdropPath?.let { "${Constants.IMAGE_BASE_URL}$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime,
    budget = budget,
    revenue = revenue,
    status = status,
    tagline = tagline,
    genres = genres.map { it.toDomain() },
    originalLanguage = originalLanguage,
    productionCompanies = productionCompanies?.map { it.name } ?: emptyList(),
    homepage = homepage
)

fun ReviewDto.toDomain() = Review(
    id = id,
    author = authorDetails?.username ?: author,
    authorAvatarPath = authorDetails?.avatarPath?.let {
        if (it.startsWith("/")) "${Constants.IMAGE_BASE_URL}$it"
        else if (it.startsWith("http")) it
        else null
    },
    rating = authorDetails?.rating,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun VideoDto.toDomain() = Video(
    id = id,
    key = key,
    name = name,
    site = site,
    type = type
)