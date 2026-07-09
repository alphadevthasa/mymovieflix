package com.example.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.core.database.dao.GenreDao
import com.example.core.database.dao.MovieDao
import com.example.core.database.dao.MovieDetailDao
import com.example.core.database.dao.MyListDao
import com.example.core.database.dao.ReviewDao
import com.example.core.database.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movieflix_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideGenreDao(database: MovieDatabase): GenreDao = database.genreDao()

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideMovieDetailDao(database: MovieDatabase): MovieDetailDao = database.movieDetailDao()

    @Provides
    fun provideMyListDao(database: MovieDatabase): MyListDao = database.myListDao()

    @Provides
    fun provideReviewDao(database: MovieDatabase): ReviewDao = database.reviewDao()
}