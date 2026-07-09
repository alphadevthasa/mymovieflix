package com.example.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "page") val page: Int,
    @Json(name = "results") val results: List<ReviewDto>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @Json(name = "id") val id: String,
    @Json(name = "author") val author: String,
    @Json(name = "author_details") val authorDetails: AuthorDetailsDto?,
    @Json(name = "content") val content: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String
)

@JsonClass(generateAdapter = true)
data class AuthorDetailsDto(
    @Json(name = "name") val name: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "avatar_path") val avatarPath: String?,
    @Json(name = "rating") val rating: Int?
)