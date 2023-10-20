package com.example.gomoku.service

import com.example.gomoku.domain.UserStatistics
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component

@Component
class StatisticService(var transactionManager: TransactionManager) {
    fun insertUserStatistics(username: String, score: Int = 0) {
        transactionManager.run {
            val userStatistics = UserStatistics(username, 0, score)
            it.statisticsRepository.insertUserStatistics(userStatistics)
            userStatistics
        }
    }

    fun getNumberOfGames(): Int =
        transactionManager.run {
            it.statisticsRepository.getNumberOfGames()
        }

    fun getRankings(): List<UserStatistics> =
        transactionManager.run {
            it.statisticsRepository.getRankings()
        }

    fun getUserRanking(username: String): UserStatistics =
        transactionManager.run {
            it.statisticsRepository.getUserRanking(username)
        }
}