package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.BoardRun
import com.example.gomoku.domain.board.stringToPositions
import com.example.gomoku.repository.GamesRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.sql.ResultSet
import java.util.*


class JdbiGamesRepository(
    private val handle: Handle, private val usersRepository: JdbiUsersRepository
) : GamesRepository {
    private fun myMapToGame(rs: ResultSet): Game {
        val gameId = rs.getObject("game_id", UUID::class.java)
        val user1Id = rs.getObject("user1_id", UUID::class.java)
        val user2Id = rs.getObject("user2_id", UUID::class.java)
        val boardPositionsStr = rs.getString("board_positions")
        val currentTurnStr = rs.getString("current_turn")
        val score = rs.getInt("score")
        val now = rs.getTimestamp("now").toInstant()
        val boardSize = rs.getInt("board_size")
        val openingStr = rs.getString("opening")
        val variantStr = rs.getString("variant")

        val user1 = usersRepository.getById(user1Id)
        val user2 = usersRepository.getById(user2Id)
        val users = Pair(user1, user2)

        val turn = currentTurnStr.toTurn()
        val currentPlayer =
            if (turn == Turn.BLACK_PIECE) Player(user1, turn)
            else Player(user2, turn)
        val board = BoardRun(boardPositionsStr.stringToPositions(boardSize), turn, boardSize)
        val rules = Rules(boardSize, openingStr.toOpening(), variantStr.toVariant())

        return Game(gameId, users, board, currentPlayer, score, now, rules)
    }

    override fun insert(game: Game) {
        handle.createUpdate(
            """
                insert into 
                dbo.games(
                game_id, user1_id, user2_id, board_positions, board_type, 
                current_turn, score, now, board_size, opening, variant
                )
                values (
                :game_id, :user1_id, :user2_id, :board_positions, :board_type, 
                :current_turn, :score, :now, :board_size, :opening, :variant
                )
                """
        )
            .bind("game_id", game.gameId)
            .bind("user1_id", game.users.first.userId)
            .bind("user2_id", game.users.second.userId)
            .bind("board_positions", game.board.positionsToString())
            .bind("board_type", game.board.typeToString())
            .bind("current_turn", game.currentPlayer.second)
            .bind("score", game.score)
            .bind("now", game.now)
            .bind("board_size", game.rules.boardDim)
            .bind("opening", game.rules.opening.toString())
            .bind("variant", game.rules.variant.toString())
            .execute()
    }

    override fun getById(id: UUID): Game =
        handle.createQuery(
            "select * from dbo.Games where game_id = :id"
        )
            .bind("id", id)
            .map { rs, _ -> myMapToGame(rs) }
            .singleOrNull() ?: throw Exception()

    override fun getAll(): List<Game> =
        handle.createQuery("select * from dbo.Games")
            .map { rs, _ -> myMapToGame(rs) }
            .list()

    override fun update(game: Game) {
        handle.createUpdate(
            """
                update dbo.Games set 
                board_positions = :board_positions,
                board_type = :board_type,
                current_turn = :current_turn,
                score = :score,
                now = :now
                where game_id = :game_id
                """
        )
            .bind("game_id", game.gameId)
            .bind("board_positions", game.board.positionsToString())
            .bind("board_type", game.board.typeToString())
            .bind("current_turn", game.currentPlayer.second)
            .bind("score", game.score)
            .bind("now", game.now)
            .execute()
    }

    /*
    * CREATE TABLE IF NOT EXISTS dbo.Games (
        game_id uuid primary key,
        user1_id uuid references dbo.Users(user_id),
        user2_id uuid references dbo.Users(user_id),
        board_positions text not null,
        board_type varchar(10) not null,
        current_turn varchar(15),
        score int,
        now timestamp not null CHECK (now <= CURRENT_TIMESTAMP),
        board_size int not null,
        opening varchar(15) not null,
        variant varchar(15) not null
    );*/

    override fun doesGameExist(id: UUID): Boolean =
        handle.createQuery("select count(*) from dbo.games where game_id = :id")
            .bind("id", id)
            .mapTo<Int>()
            .single() == 1
}