package com.example.gomoku.repository

import com.example.gomoku.domain.Game
import java.util.*


interface GamesRepository {
    fun insert(game: Game)

    fun getById(id: UUID): Game

    fun getAll(): List<Game>

    fun update(game: Game)

    fun doesGameExist(id: UUID): Boolean

    fun getLatestGame(userID: UUID) : Game

    fun doesLatestExist(userID: UUID) : Boolean
}