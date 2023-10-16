package com.example.gomoku.repository

import com.example.gomoku.domain.game.Game
import com.example.gomoku.domain.Lobby
import java.util.*


interface GamesRepository {
    fun getById(id: UUID): Game

    fun update(game: Game): Boolean

    fun insert(game: Game) : Boolean

    fun doesGameExist(id: UUID): Boolean
}