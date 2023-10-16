package com.example.gomoku.domain.player

import java.util.*


data class UserStatisticalInfo(val userId: UUID, val score: Int, val playedGames: Int)