package com.example.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.database.dao.GenreDao
import com.example.core.database.dao.MovieDao
import com.example.core.database.dao.MovieDetailDao
import com.example.core.database.dao.MyListDao
import com.example.core.database.dao.ReviewDao
import com.example.core.database.entity.GenreEntity
import com.example.core.database.entity.MovieDetailEntity
import com.example.core.database.entity.MovieEntity
import com.example.core.database.entity.MyListEntity
import com.example.core.database.entity.ReviewEntity

@Database(
    entities = [
        GenreEntity::class,
        MovieEntity::class,
        MovieDetailEntity::class,
        MyListEntity::class,
        ReviewEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun myListDao(): MyListDao
    abstract fun reviewDao(): ReviewDao
}