package com.example.badmintonrankers.data.repository

import androidx.lifecycle.LiveData
import com.example.badmintonrankers.data.model.Players
import com.example.badmintonrankers.data.providers.PlayerDao

class BadminRepository(private val playerDao: PlayerDao){

    val getPlayer: LiveData<List<Players>> = playerDao.getPlayerData()

    fun addPlayer(players: Players){
        playerDao.addPlayer(players)
    }
}