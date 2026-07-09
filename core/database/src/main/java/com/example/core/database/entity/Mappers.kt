package com.example.core.database.entity

import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video

fun GenreEntity.toDomain() = Genre(id = id, name = name)

fun Genre.toEntity() = GenreEntity(id = id, name = name)

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds.split(",").mapNotNull { it.toIntOrNull() },
    originalLanguage = originalLanguage,
    popularity = popularity,
    isAdult = isAdult
)

fun Movie.toEntity(genreId: Int) = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds.joinToString(","),
    originalLanguage = originalLanguage,
    popularity = popularity,
    isAdult = isAdult,
    genreId = genreId
)

fun MyListEntity.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds.split(",").mapNotNull { it.toIntOrNull() },
    originalLanguage = originalLanguage,
    popularity = popularity,
    isAdult = isAdult
)

fun Movie.toMyListEntity() = MyListEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genreIds = genreIds.joinToString(","),
    originalLanguage = originalLanguage,
    popularity = popularity,
    isAdult = isAdult
)

fun MovieDetailEntity.toDomain(): MovieDetail {
    val genreList = genres?.split(";")?.mapNotNull {
        val parts = it.split(":")
        if (parts.size == 2) Genre(parts[0].toIntOrNull() ?: 0, parts[1]) else null
    } ?: emptyList()

    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        status = status,
        tagline = tagline,
        genres = genreList,
        originalLanguage = originalLanguage,
        productionCompanies = productionCompanies?.split(",") ?: emptyList(),
        homepage = homepage
    )
}

fun MovieDetail.toEntity() = MovieDetailEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    runtime = runtime,
    budget = budget,
    revenue = revenue,
    status = status,
    tagline = tagline,
    genres = genres.joinToString(";") { "${it.id}:${it.name}" },
    originalLanguage = originalLanguage,
    productionCompanies = productionCompanies.joinToString(","),
    homepage = homepage
)

fun ReviewEntity.toDomain() = Review(
    id = id,
    author = author,
    authorAvatarPath = authorAvatarPath,
    rating = rating,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Review.toEntity(movieId: Int) = ReviewEntity(
    id = id,
    author = author,
    authorAvatarPath = authorAvatarPath,
    rating = rating,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    movieId = movieId
)