package com.example.gomoku.repository.jdbi_interfaces

import com.example.gomoku.domain.Game
import java.util.*


interface GamesRepository {
    fun getById(id: UUID): Game

    fun update(game: Game)

    fun insert(game: Game)

    fun doesGameExist(id: UUID): Boolean
}