package com.example.submission1.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission1.core.data.source.local.entity.MovieEntity
import com.example.submission1.core.data.source.local.entity.TVEntity

@Database(entities = [MovieEntity::class, TVEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao() : FilmDao
    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getAppDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorite_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}