package com.example.gomoku.service

import com.example.gomoku.domain.UserRanking
import com.example.gomoku.repository.jdbi_interfaces.TransactionManager
import org.springframework.stereotype.Component

@Component
class StatisticService(var transactionManager: TransactionManager) {
    fun getNumberOfGames(): Int =
        transactionManager.run {
            it.statisticsRepository.getNumberOfGames()
        }

    fun getRankings(): List<UserRanking> =
        transactionManager.run {
            it.statisticsRepository.getRankings()
        }

    fun getUserRanking(username: String): UserRanking =
        transactionManager.run {
            it.statisticsRepository.getUserRanking(username)
        }
}