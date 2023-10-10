package com.example.gomoku.repository.jdbi_interfaces


interface Transaction {
    val usersRepository: UsersRepository

    val gamesRepository: GamesRepository

    val statisticsRepository: StatisticsRepository

    fun rollback()
}