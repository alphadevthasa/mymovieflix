package com.example.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.database.entity.MyListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {

    @Query("SELECT * FROM my_list ORDER BY added_at DESC")
    fun getAllMovies(): Flow<List<MyListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MyListEntity)

    @Query("DELETE FROM my_list WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM my_list WHERE id = :movieId)")
    suspend fun isMovieSaved(movieId: Int): Boolean
}
