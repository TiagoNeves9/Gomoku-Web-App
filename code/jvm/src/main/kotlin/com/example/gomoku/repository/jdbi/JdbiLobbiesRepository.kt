package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.Rules
import com.example.gomoku.domain.toOpening
import com.example.gomoku.domain.toVariant
import com.example.gomoku.repository.LobbiesRepository
import org.jdbi.v3.core.Handle
import java.sql.ResultSet
import java.util.*


class JdbiLobbiesRepository(private val handle: Handle) : LobbiesRepository {
    private fun myMapToLobby(rs: ResultSet): Lobby {
        val lobbyIdStr = rs.getString("lobby_id")
        val hostUserIdStr = rs.getString("host")
        val boardSize = rs.getInt("board_size")
        val opening = rs.getString("opening")
        val variant = rs.getString("variant")

        val lobbyId = UUID.fromString(lobbyIdStr)
        val hostUserId = UUID.fromString(hostUserIdStr)
        val rules = Rules(boardSize, opening.toOpening(), variant.toVariant())

        return Lobby(lobbyId, hostUserId, rules)
    }

    override fun insert(lobby: Lobby) {
        handle.createUpdate(
            """
                insert into dbo.Lobbies (lobby_id, host, board_size, opening, variant) 
                values (:lobby_id, :host, :board_size, :opening, :variant)
                """
        )
            .bind("lobby_id", lobby.lobbyId)
            .bind("host", lobby.hostUserId)
            .bind("board_size", lobby.rules.boardDim)
            .bind("opening", lobby.rules.opening)
            .bind("variant", lobby.rules.variant)
            .execute()
    }

    //Get the unique lobby (if exists)
    override fun getLobby(rules: Rules): Lobby? =
        handle.createQuery(
            "select * from dbo.Lobbies " +
                    "where board_size = :board_size " +
                    "and opening = :opening " +
                    "and variant = :variant"
        )
            .bind("board_size", rules.boardDim)
            .bind("opening", rules.opening)
            .bind("variant", rules.variant)
            .map { rs, _ -> myMapToLobby(rs) }
            .singleOrNull()

    override fun delete(lobby: Lobby) {
        handle.createUpdate(
            """
                delete from dbo.Lobbies
                where lobby_id = :lobby_id
                """
        )
            .bind("lobby_id", lobby.lobbyId)
            .execute()
    }
}