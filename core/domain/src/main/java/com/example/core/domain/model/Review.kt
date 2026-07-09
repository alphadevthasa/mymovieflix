package com.example.core.domain.model

data class Review(
    val id: String,
    val author: String,
    val authorAvatarPath: String?,
    val rating: Int?,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)