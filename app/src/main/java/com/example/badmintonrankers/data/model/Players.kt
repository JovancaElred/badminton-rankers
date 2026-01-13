package com.example.badmintonrankers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranker_players")
data class Players(
    @PrimaryKey(autoGenerate = true)
    var uuid: Int,
    val displayName: String?,
    val mmr: Int?,
    val rank: String?,
    val wr: Int?,
    val matches: Int?,
    val win: Int?,
    val lose: Int?,
    val peakMmr: Int?,
    val lowestMmr: Int?
)