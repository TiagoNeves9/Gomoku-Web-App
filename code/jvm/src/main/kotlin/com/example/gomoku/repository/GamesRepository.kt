package com.example.gomoku.repository

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.Lobby
import java.util.*


interface GamesRepository {
    fun insert(game: Game)

    fun getById(id: UUID): Game

    fun getAll(): List<Game>

    fun update(game: Game)

    fun doesGameExist(id: UUID): Boolean
}