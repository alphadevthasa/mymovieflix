package com.example.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "runtime") val runtime: Int?,
    @ColumnInfo(name = "budget") val budget: Long?,
    @ColumnInfo(name = "revenue") val revenue: Long?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "tagline") val tagline: String?,
    @ColumnInfo(name = "genres") val genres: String?,
    @ColumnInfo(name = "original_language") val originalLanguage: String?,
    @ColumnInfo(name = "production_companies") val productionCompanies: String?,
    @ColumnInfo(name = "homepage") val homepage: String?
)