package com.example.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "results") val results: List<VideoDto>
)

@JsonClass(generateAdapter = true)
data class VideoDto(
    @Json(name = "id") val id: String,
    @Json(name = "key") val key: String,
    @Json(name = "name") val name: String,
    @Json(name = "site") val site: String,
    @Json(name = "type") val type: String
)