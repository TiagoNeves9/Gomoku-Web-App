package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Game
import com.example.gomoku.repository.GamesRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.util.*


class JdbiGamesRepository(private val handle: Handle) : GamesRepository {
    override fun getById(id: UUID): Game =
        handle.createQuery(
            "select id, last_move, game_state, board, score, " +
                    "player_x, player_o from dbo.Games where id = :id"
        )
            .bind("id", id)
            .mapTo(Game::class.java)
            .singleOrNull() ?: throw Exception()

    override fun update(game: Game) {
        handle.createUpdate(
            """
                update dbo.Games set 
                board = :board,
                current_player = :currentPlayer,
                score = :score,
                now = :now
                where id = :id
                """
        )
            .bind("board", game.board)
            .bind("currentPlayer", game.currentPlayer)
            .bind("score", game.score)
            .bind("now", game.now)
            .execute()
    }

    override fun insert(game: Game) {
        //TODO we are not saving the board
        handle.createUpdate(
            """
                insert into dbo.games(game_id, user1_id, user2_id, current_player, score, now)
                values (:game_id, :user1_id, :user2_id, :current_player, :score, :now)
                """
        )
            .bind("game_id", game.gameId)
            .bind("user1_id", game.users.first.userId)
            .bind("user2_id", game.users.second.userId)
            .bind("current_player", game.currentPlayer.second.color)
            .bind("score", game.score)
            .bind("now", game.now)
            .execute()
    }

    override fun doesGameExist(id: UUID): Boolean =
        handle.createQuery("select count(*) from dbo.games where id = :id")
            .bind("id", id)
            .mapTo<Int>()
            .single() == 1
}