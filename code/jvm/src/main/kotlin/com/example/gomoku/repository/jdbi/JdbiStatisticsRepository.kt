package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.UserRanking
import com.example.gomoku.repository.jdbi_interfaces.StatisticsRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo


class JdbiStatisticsRepository(private val handle: Handle) : StatisticsRepository {
    data class StatisticsModel(val username: String, val playedGames: Int, val score: Int) {
        fun getUserRanking() = UserRanking(username, playedGames, score)
    }

    override fun getRankings(): List<UserRanking> =
        handle.createQuery(
            "select username, played_games, score from dbo.statistics order by score DESC"
        ).mapTo<StatisticsModel>()
            .list()
            .map {
                it.getUserRanking()
            }

    override fun getNumberOfGames(): Int =
        handle.createQuery(
            "select count(id) from dbo.games"
        )
            .mapTo<Int>()
            .first()

    override fun getUserRanking(username: String): UserRanking =
        handle.createQuery(
            "select username, played_games, score from dbo.statistics where username =:username"
        )
            .bind("username", username)
            .mapTo<StatisticsModel>()
            .first().getUserRanking()

    override fun updateUserRanking(username: String, score: Int): Boolean =
        handle.createUpdate(
            "update dbo.statistics set score=:score where username = :username"
        )
            .bind("username", username)
            .bind("score", score)
            .execute()
            .compareTo(1) == 0
}