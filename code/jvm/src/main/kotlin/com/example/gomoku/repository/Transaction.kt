package com.example.gomoku.repository


interface Transaction {
    val usersRepository: UsersRepository

    val gamesRepository: GamesRepository

    val statisticsRepository: StatisticsRepository

    val lobbiesRepository: LobbiesRepository

    fun rollback()
}