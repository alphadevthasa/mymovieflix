package com.example.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreResponse(
    @Json(name = "genres") val genres: List<GenreDto>
)

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)