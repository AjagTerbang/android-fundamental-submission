package com.example.androidfundamentalsubmission1.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent

@Dao
interface FavoriteEventDao {
    @Insert
    suspend fun insertFavoriteEvent(favoriteEvent: FavoriteEvent)

    @Query("SELECT * FROM FavoriteEvent")
    fun getAllFavoriteEvent(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    suspend fun getFavoriteEventById(id: String): FavoriteEvent

    @Query("DELETE FROM FavoriteEvent WHERE id = :id")
    suspend fun deleteFavoriteEventById(id: String)

    @Query("SELECT * FROM FavoriteEvent WHERE id = :id")
    fun getFavoriteEventByIdLive(id: String): LiveData<FavoriteEvent>
}