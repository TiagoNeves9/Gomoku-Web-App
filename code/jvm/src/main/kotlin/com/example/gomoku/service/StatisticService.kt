package com.example.gomoku.service

import com.example.gomoku.domain.UserRanking
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component

@Component
class StatisticService(
    var transactionManager: TransactionManager
) {

    fun getNumberOfGames() : Int {
        return transactionManager.run {
            it.statisticsRepository.getNumberOfGames()
        }
    }

    fun getRankings() : List<UserRanking> {
        return transactionManager.run {
            it.statisticsRepository.getRankings()
        }
    }

    fun getUserRanking(username : String): UserRanking {
        return transactionManager.run {
            it.statisticsRepository.getUserRanking(username)
        }
    }


}