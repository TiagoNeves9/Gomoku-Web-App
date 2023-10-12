package com.example.gomoku.repository


interface Transaction {
    val usersRepository: UsersRepository

    val gamesRepository: GamesRepository

    val lobbiesRepository: LobbiesRepository

    val statisticsRepository: StatisticsRepository

    fun rollback()
}