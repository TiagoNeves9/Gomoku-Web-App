package com.example.gomoku.repository

import com.example.gomoku.domain.UserStatistics


interface StatisticsRepository {
    fun insertUserStatistics(userStatistics: UserStatistics)

    fun getRankings(): List<UserStatistics>

    fun getNumberOfGames(): Int

    fun getUserRanking(username: String): UserStatistics

    fun updateUserRanking(username: String, score: Int): Boolean
}