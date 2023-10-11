package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Game
import com.example.gomoku.repository.GamesRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*


class JdbiGamesRepository(private val handle: Handle) : GamesRepository {
    override fun getById(id: UUID): Game =
        handle
            .createQuery(
                "select id, last_move, game_state, board, score, " +
                        "player_x, player_o from dbo.Games where id = :id"
            )
            .bind("id", id)
            .mapTo(Game::class.java)
            .singleOrNull() ?: throw Exception()

    override fun update(game: Game) {
        handle
            .createUpdate(
                """
                update dbo.Games set 
                last_move =:last_move,
                game_state =:game_state,
                board =:board,
                score =:score
                where id =:id
                """
            )
            .bind("id", game.id)
            .bind("last_move", game.updated)
            .bind("game_state", game.state)
            .bind("board", game.board)
            .bind("score", game.score)
            .execute()
    }

    override fun insert(game: Game) {
        handle
            .createUpdate(
                """
                insert into dbo.games(id, last_move, game_state, board, score, player_x, player_o)
                values(:game_id, :last_move, :state, :board, :score, :player_x, :player_o)
                """
            )
            .bind("id", game.id)
            .bind("last_move", game.updated)
            .bind("state", game.state)
            .bind("board", game.board)
            .bind("score", game.score)
            .bind("player_x", game.playerB)
            .bind("player_o", game.playerW)
    }

    override fun doesGameExist(id: UUID): Boolean {
        return handle
            .createQuery("select count(*) from dbo.games where id = :id")
            .bind("id", id)
            .mapTo<Int>()
            .single() == 1
    }
}