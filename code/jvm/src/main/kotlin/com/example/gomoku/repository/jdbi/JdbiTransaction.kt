package com.example.gomoku.repository.jdbi

import com.example.gomoku.repository.jdbi_interfaces.GamesRepository
import com.example.gomoku.repository.jdbi_interfaces.StatisticsRepository
import com.example.gomoku.repository.jdbi_interfaces.Transaction
import com.example.gomoku.repository.jdbi_interfaces.UsersRepository
import org.jdbi.v3.core.Handle


class JdbiTransaction(private val handle: Handle) : Transaction {
    override val usersRepository: UsersRepository by lazy { JdbiUsersRepository(handle) }
    override val gamesRepository: GamesRepository by lazy { JdbiGamesRepository(handle) }
    override val statisticsRepository: StatisticsRepository by lazy { JdbiStatisticsRepository(handle) }

    override fun rollback() {
        handle.rollback()
    }
}