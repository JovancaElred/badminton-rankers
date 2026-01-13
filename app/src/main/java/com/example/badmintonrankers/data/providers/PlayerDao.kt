package com.example.badmintonrankers.data.providers

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.badmintonrankers.data.model.Players

interface PlayerDao {
    @Query("SELECT * FROM ranker_players")
    fun getPlayerData(): LiveData<List<Players>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlayer(players: Players)
}