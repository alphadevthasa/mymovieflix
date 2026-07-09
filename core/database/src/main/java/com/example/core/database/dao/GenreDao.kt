package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.GenreEntity

@Dao
interface GenreDao {

    @Query("SELECT * FROM genres ORDER BY name ASC")
    suspend fun getAllGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}