package com.example.gomoku.repository

import com.example.gomoku.domain.UserRanking

interface StatisticsRepository {
    fun getRankings() : List<UserRanking>
}

