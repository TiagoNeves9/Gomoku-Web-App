package com.example.gomoku.repository.jdbi

import com.example.gomoku.repository.*
import org.jdbi.v3.core.Handle


class JdbiTransaction(private val handle: Handle) : Transaction {
    override val usersRepository: UsersRepository by lazy { JdbiUsersRepository(handle) }

    override val gamesRepository: GamesRepository by lazy { JdbiGamesRepository(handle) }

    override val statisticsRepository: StatisticsRepository by lazy { JdbiStatisticsRepository(handle) }

    override val lobbiesRepository: LobbiesRepository by lazy { JdbiLobbiesRepository(handle) }

    override fun rollback() {
        handle.rollback()
    }
}