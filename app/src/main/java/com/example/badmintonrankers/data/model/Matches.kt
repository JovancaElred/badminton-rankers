package com.example.badmintonrankers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("matches")
data class Matches(
    @PrimaryKey(autoGenerate = true)
    val matchId: Long = 0,
    val date: Long?,
    val team1Score: Int?,
    val team2Score: Int?,
    val winner: Int?,
)

@Entity("match_player")
data class MatchPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matchId: Long,
    val playerId: Long,
    val team: Int,
    val mmrBefore: Int,
    val mmrAfter: Int,
)