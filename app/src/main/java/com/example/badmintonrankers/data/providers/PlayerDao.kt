package com.example.badmintonrankers.data.providers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.badmintonrankers.data.model.Players
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM ranker_players")
    fun getPlayerData(): Flow<List<Players>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlayer(players: Players)
}