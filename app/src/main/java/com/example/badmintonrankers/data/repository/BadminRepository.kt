package com.example.badmintonrankers.data.repository

import androidx.lifecycle.LiveData
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.data.providers.PlayerDao
import kotlinx.coroutines.flow.Flow

class BadminRepository(private val playerDao: PlayerDao){

    fun addPlayer(players: Players){
        playerDao.addPlayer(players)
    }

    fun getPlayer(): Flow<List<Players>> {
        return playerDao.getPlayerData()
    }
}