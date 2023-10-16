package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.game.Board
import com.example.gomoku.domain.game.Game
import com.example.gomoku.repository.GamesRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import kotlinx.serialization.json.Json
import org.jdbi.v3.core.statement.Update
import org.postgresql.util.PGobject
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

    override fun update(game: Game)  : Boolean =
        handle.createUpdate(
            """
                update dbo.Games set 
                board = :board,
                game_state = :state,
                score = :score,
                last_move = :instant
                where id = :id
                """
        )
            .bind("board", game.board)
            .bind("state", game.state)
            .bind("score", game.score)
            .bind("instant", game.updated)
            .execute()
            .compareTo(1) == 0


    override fun insert(game: Game) : Boolean =
        handle.createUpdate(
            """
                insert into 
                dbo.games(id, player_x, player_o, board, game_state, last_move, score)
                values (:game_id, :user1_id, :user2_id, :board, :state, :instant, :score)
                """
        )
            .bind("game_id", game.id)
            .bind("user1_id", game.playerB.userId)
            .bind("user2_id", game.playerW.userId)
            .bindBoard("board", game.board)
            .bind("state", game.state.toString())
            .bind("instant", game.updated)
            .bind("score", game.score)
            .execute()
            .compareTo(1) == 0


    override fun doesGameExist(id: UUID): Boolean =
        handle.createQuery("select count(*) from dbo.games where id = :id")
            .bind("id", id)
            .mapTo<Int>()
            .single() == 1


    companion object {
        private fun Update.bindBoard(boardName: String, board: Board) = run {
            bind(
                boardName,
                PGobject().apply{
                    type = "jsonb"
                    value = serializeBoardToJson(board)
                }
            )
        }

        private fun serializeBoardToJson(board : Board) : String = Json.encodeToString(board)
        fun deserializeBoardFromJson(jsonBoard : String) : Board = Json.decodeFromString(jsonBoard)
    }

}