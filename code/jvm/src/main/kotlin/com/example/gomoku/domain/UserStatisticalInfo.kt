package com.example.gomoku.domain

import java.util.*

data class UserStatisticalInfo(
    val userId: UUID,
    val score: Int,
    val playedGames: Int
)