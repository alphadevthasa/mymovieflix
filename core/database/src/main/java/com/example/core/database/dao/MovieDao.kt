package com.example.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE genre_id = :genreId ORDER BY popularity DESC")
    fun getMoviesByGenre(genreId: Int): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE genre_id = :genreId ORDER BY popularity DESC")
    suspend fun getMoviesByGenreList(genreId: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE genre_id = :genreId")
    suspend fun deleteMoviesByGenre(genreId: Int)
}