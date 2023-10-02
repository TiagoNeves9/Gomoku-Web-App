package com.example.gomoku.repository.jdbi

import org.jdbi.v3.core.Jdbi
import com.example.gomoku.domain.Game
import com.example.gomoku.repository.GamesRepository
import java.util.*


class JdbiGamesRepository(private val jdbi : Jdbi) : GamesRepository {

    override fun getById(id : UUID): Game? =
        jdbi.withHandle<Game?, Exception> { handle ->
            handle.createQuery("select id, last_move, game_state, board, score, player_x, player_o from dbo.Games where id = :id")
                .bind("id", id)
                .mapTo(Game::class.java)
                .singleOrNull()
        }

    override fun update(game: Game) {

        return
    }

    override fun insert(game: Game) {

        return
    }

    override fun doesGameExist(id: UUID): Boolean {
        TODO("Not yet implemented")
    }

}