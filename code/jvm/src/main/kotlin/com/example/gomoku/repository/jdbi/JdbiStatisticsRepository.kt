package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.UserStatistics
import com.example.gomoku.repository.StatisticsRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo


class JdbiStatisticsRepository(private val handle: Handle) : StatisticsRepository {
    data class StatisticsModel(val username: String, val playedGames: Int, val score: Int) {
        fun getUserRanking() = UserStatistics(username, playedGames, score)
    }

    override fun insertUserStatistics(userStatistics: UserStatistics) {
        handle.createUpdate(
            """
                insert into dbo.statistics(username, played_games, score) 
                values (:username, :nGames, :score)
                """
        )
            .bind("username", userStatistics.user)
            .bind("nGames", userStatistics.nGames)
            .bind("score", userStatistics.score)
            .execute()
    }

    override fun getRankings(): List<UserStatistics> =
        handle.createQuery(
            "select username, played_games, score " +
                    "from dbo.statistics order by score DESC"
        ).mapTo<StatisticsModel>()
            .list()
            .map {
                it.getUserRanking()
            }

    override fun getNumberOfGames(): Int =
        handle.createQuery(
            "select count(game_id) from dbo.games"
        )
            .mapTo<Int>()
            .first()

    override fun getUserRanking(username: String): UserStatistics =
        handle.createQuery(
            "select username, played_games, score from dbo.statistics " +
                    "where username =:username"
        )
            .bind("username", username)
            .mapTo<StatisticsModel>()
            .first()
            .getUserRanking()

    override fun updateUserRanking(username: String, score: Int): Boolean =
        handle.createUpdate(
            "update dbo.statistics " +
                    "set score = score + :score, played_games = played_games + 1 " +
                    "where username = :username"
        )
            .bind("username", username)
            .bind("score", score)
            .execute()
            .compareTo(1) == 0
}