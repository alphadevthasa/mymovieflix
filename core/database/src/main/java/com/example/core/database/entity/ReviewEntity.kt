package com.example.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "author_avatar_path") val authorAvatarPath: String?,
    @ColumnInfo(name = "rating") val rating: Int?,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
    @ColumnInfo(name = "movie_id") val movieId: Int
)