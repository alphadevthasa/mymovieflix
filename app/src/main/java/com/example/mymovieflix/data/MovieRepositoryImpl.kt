package com.example.mymovieflix.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.common.util.Constants
import com.example.core.database.dao.GenreDao
import com.example.core.database.dao.MovieDao
import com.example.core.database.dao.MovieDetailDao
import com.example.core.database.dao.MyListDao
import com.example.core.database.dao.ReviewDao
import com.example.core.database.entity.toDomain
import com.example.core.database.entity.toEntity
import com.example.core.database.entity.toMyListEntity
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.model.Review
import com.example.core.domain.model.Video
import com.example.core.domain.repository.MovieRepository
import com.example.core.network.api.TmdbApi
import com.example.core.network.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val movieDetailDao: MovieDetailDao,
    private val myListDao: MyListDao,
    private val reviewDao: ReviewDao
) : MovieRepository {

    override fun getGenres(): Flow<List<Genre>> = flow {
        val cached = genreDao.getAllGenres()
        if (cached.isNotEmpty()) {
            emit(cached.map { it.toDomain() })
        }
        try {
            val response = api.getGenres()
            val genres = response.genres.map { it.toDomain() }
            genreDao.deleteAll()
            genreDao.insertGenres(genres.map { it.toEntity() })
            emit(genres)
        } catch (e: Exception) {
            if (cached.isEmpty()) emit(defaultMovieGenres())
        }
    }

    private fun defaultMovieGenres(): List<Genre> = listOf(
        Genre(28, "Action"),
        Genre(12, "Adventure"),
        Genre(16, "Animation"),
        Genre(35, "Comedy"),
        Genre(80, "Crime"),
        Genre(99, "Documentary"),
        Genre(18, "Drama"),
        Genre(10751, "Family"),
        Genre(14, "Fantasy"),
        Genre(36, "History"),
        Genre(27, "Horror"),
        Genre(10402, "Music"),
        Genre(9648, "Mystery"),
        Genre(10749, "Romance"),
        Genre(878, "Science Fiction"),
        Genre(10770, "TV Movie"),
        Genre(53, "Thriller"),
        Genre(10752, "War"),
        Genre(37, "Western")
    )

    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(api, movieDao, genreId) }
        ).flow
    }

    override suspend fun getMoviesByGenreList(genreId: Int): List<Movie> {
        return try {
            api.getMoviesByGenre(genreId, 1).results.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun searchMoviesList(query: String): List<Movie> {
        return try {
            api.searchMovies(query, 1).results.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> = flow {
        val cached = movieDetailDao.getMovieDetail(movieId)
        if (cached != null) {
            emit(cached.toDomain())
        }
        try {
            val response = api.getMovieDetail(movieId)
            val detail = response.toDomain()
            movieDetailDao.insertMovieDetail(detail.toEntity())
            emit(detail)
        } catch (e: Exception) {
            if (cached == null) throw e
        }
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ReviewPagingSource(api, reviewDao, movieId) }
        ).flow
    }

    override fun getMovieVideos(movieId: Int): Flow<List<Video>> = flow {
        try {
            val response = api.getMovieVideos(movieId)
            val videos = response.results
                .filter { it.site == "YouTube" && it.type == "Trailer" }
                .map { it.toDomain() }
            emit(videos)
        } catch (_: Exception) {
            emit(emptyList())
        }
    }

    override fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { TrendingPagingSource(api) }
        ).flow
    }

    override suspend fun getTrendingMoviesList(): List<Movie> {
        return try {
            api.getTrending().results.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { TopRatedPagingSource(api) }
        ).flow
    }

    override suspend fun getTopRatedMoviesList(): List<Movie> {
        return try {
            api.getTopRated(page = 1).results.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NowPlayingPagingSource(api) }
        ).flow
    }

    override suspend fun getNowPlayingMoviesList(): List<Movie> {
        return try {
            api.getNowPlaying().results.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { SimilarMoviesPagingSource(api, movieId) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(api, query) }
        ).flow
    }

    override suspend fun toggleMyList(movie: Movie) {
        if (myListDao.isMovieSaved(movie.id)) {
            myListDao.deleteMovie(movie.id)
        } else {
            myListDao.insertMovie(movie.toMyListEntity())
        }
    }

    override fun getMyList(): Flow<List<Movie>> = myListDao.getAllMovies().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun isMovieSaved(movieId: Int): Boolean = myListDao.isMovieSaved(movieId)
}

class MoviePagingSource(
    private val api: TmdbApi,
    private val movieDao: MovieDao,
    private val genreId: Int
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getMoviesByGenre(genreId, page)
            val movies = response.results.map { it.toDomain() }
            movieDao.insertMovies(movies.map { it.toEntity(genreId) })
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            val cached = movieDao.getMoviesByGenreList(genreId)
            LoadResult.Page(
                data = cached.map { it.toDomain() },
                prevKey = null,
                nextKey = null
            )
        }
    }
}

class ReviewPagingSource(
    private val api: TmdbApi,
    private val reviewDao: ReviewDao,
    private val movieId: Int
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val page = params.key ?: 1
            val response = api.getMovieReviews(movieId, page)
            val reviews = response.results.map { it.toDomain() }
            reviewDao.insertReviews(reviews.map { it.toEntity(movieId) })
            LoadResult.Page(
                data = reviews,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            val cached = reviewDao.getReviewsByMovieList(movieId)
            LoadResult.Page(
                data = cached.map { it.toDomain() },
                prevKey = null,
                nextKey = null
            )
        }
    }
}

class TrendingPagingSource(private val api: TmdbApi) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getTrending()
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            LoadResult.Page(emptyList(), null, null)
        }
    }
}

class TopRatedPagingSource(private val api: TmdbApi) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getTopRated(page = page)
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            LoadResult.Page(emptyList(), null, null)
        }
    }
}

class NowPlayingPagingSource(private val api: TmdbApi) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getNowPlaying()
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            LoadResult.Page(emptyList(), null, null)
        }
    }
}

class SimilarMoviesPagingSource(
    private val api: TmdbApi,
    private val movieId: Int
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.getSimilarMovies(movieId, page)
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            LoadResult.Page(emptyList(), null, null)
        }
    }
}

class SearchPagingSource(
    private val api: TmdbApi,
    private val query: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = api.searchMovies(query, page)
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (_: Exception) {
            LoadResult.Page(emptyList(), null, null)
        }
    }
}
