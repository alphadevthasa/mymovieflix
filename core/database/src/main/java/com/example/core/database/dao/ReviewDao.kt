package com.example.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews WHERE movie_id = :movieId ORDER BY created_at DESC")
    fun getReviewsByMovie(movieId: Int): PagingSource<Int, ReviewEntity>

    @Query("SELECT * FROM reviews WHERE movie_id = :movieId ORDER BY created_at DESC")
    suspend fun getReviewsByMovieList(movieId: Int): List<ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<ReviewEntity>)

    @Query("DELETE FROM reviews WHERE movie_id = :movieId")
    suspend fun deleteReviewsByMovie(movieId: Int)
}