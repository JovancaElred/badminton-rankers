package com.example.badmintonrankers.data.model

data class Players(
    val uuid: String? = null,
    val displayName: String? = null,
    val mmr: Int? = null,
    val rank: String? = null,
    val wr: Int? = null,
    val matches: Int? = null,
    val win: Int? = null,
    val lose: Int? = null,
    val peakMmr: Int? = null,
    val lowestMmr: Int? = null,
)