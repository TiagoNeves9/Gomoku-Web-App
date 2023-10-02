package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.UserRanking
import com.example.gomoku.repository.StatisticsRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo


class JdbiStatisticsRepository(
    private val handle : Handle
) : StatisticsRepository {
    override fun getRankings(): List<UserRanking> {
        return handle.createQuery(
            """
               select username, played_games, score from dbo.statistics by score DESC
            """
        ).mapTo<StatisticsModel>()
            .list()
            .map {
                it.getUserRanking()
            }

    }


    data class StatisticsModel(
        val username : String,
        val playedGames : Int,
        val score : Int
    ) {
        fun getUserRanking() = UserRanking(username, playedGames, score)
    }
}