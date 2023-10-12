package com.example.gomoku.repository

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.Lobby
import java.util.*


interface GamesRepository {
    fun getById(id: UUID): Game

    fun update(game: Game)

    fun insert(game: Game)

    fun doesGameExist(id: UUID): Boolean
}