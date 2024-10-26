package com.example.androidfundamentalsubmission1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidfundamentalsubmission1.dao.FavoriteEventDao
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent


@Database(entities = [FavoriteEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteEventDao(): FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "favorites.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}