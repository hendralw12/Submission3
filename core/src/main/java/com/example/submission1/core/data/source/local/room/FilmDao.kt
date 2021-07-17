package com.example.submission1.core.data.source.local.room

import androidx.room.*
import com.example.submission1.core.data.source.local.entity.MovieEntity
import com.example.submission1.core.data.source.local.entity.TVEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Update
    fun UpdateFavMovie(movie:MovieEntity)

    @Update
    fun UpdateFavTV(tv:TVEntity)

    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tv")
    fun getAllTV(): Flow<List<TVEntity>>

    @Query("SELECT * FROM movie where isFavorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tv where isFavorite = 1")
    fun getFavoriteTV(): Flow<List<TVEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTV(tv: List<TVEntity>)

}