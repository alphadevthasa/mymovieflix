package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.MovieDetailEntity

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(detail: MovieDetailEntity)
}