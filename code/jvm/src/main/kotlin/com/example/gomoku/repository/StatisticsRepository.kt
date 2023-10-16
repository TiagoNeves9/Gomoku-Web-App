package com.example.gomoku.repository

import com.example.gomoku.domain.player.UserRanking


interface StatisticsRepository {
    fun getRankings(): List<UserRanking>

    fun getNumberOfGames(): Int

    fun getUserRanking(username: String): UserRanking

    fun updateUserRanking(username: String, score: Int): Boolean
}