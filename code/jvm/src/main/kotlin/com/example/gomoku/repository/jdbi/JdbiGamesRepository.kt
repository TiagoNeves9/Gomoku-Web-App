package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Game
import com.example.gomoku.repository.GamesRepository
import java.util.*


class JdbiGamesRepository : GamesRepository {

    override fun getById(id : UUID) : Game? {

        return null
    }

    override fun update(game: Game) {

        return
    }

    override fun insert(game: Game) {

        return
    }

}