package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.Rules
import com.example.gomoku.domain.toOpening
import com.example.gomoku.domain.toVariant
import com.example.gomoku.http.model.LobbyOutputModel
import com.example.gomoku.repository.LobbiesRepository
import com.example.gomoku.service.Exceptions
import org.jdbi.v3.core.Handle
import java.sql.ResultSet
import java.util.*


class JdbiLobbiesRepository(private val handle: Handle) : LobbiesRepository {
    private fun myMapToLobby(rs: ResultSet): LobbyOutputModel {
        val lobbyIdStr = rs.getString("lobby_id")
        val hostUserIdStr = rs.getString("host")
        val boardSize = rs.getInt("board_size")
        val opening = rs.getString("opening")
        val variant = rs.getString("variant")

        val lobbyId = UUID.fromString(lobbyIdStr)
        val hostUserId = UUID.fromString(hostUserIdStr)

        val lobbyOutputModel = LobbyOutputModel(lobbyId, hostUserId, boardSize, opening, variant)
        //println(lobbyOutputModel)
        return lobbyOutputModel
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
    override fun getLobby(rules: Rules): LobbyOutputModel? =
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

    override fun getAll(): List<LobbyOutputModel> =
        handle.createQuery(
            "select * from dbo.Lobbies"
        )
            .map { rs, _ -> myMapToLobby(rs) }
            .list()

    override fun delete(lobby: LobbyOutputModel): Int {
        return handle.createUpdate(
            "delete from dbo.Lobbies where lobby_id = :id"
        )
            .bind("id", lobby.lobbyId)
            .execute()
    }

    override fun deleteUserLobby(userId: UUID): Int {
        val lobby = handle.createQuery(
            "select * from dbo.lobbies where host =:userId"
        )
            .bind("userId", userId)
            .map { rs, _ -> myMapToLobby(rs) }
            .singleOrNull()
        if (lobby != null) {
            return handle.createUpdate(
                "DELETE from dbo.lobbies where lobby_id =:id"
            )
                .bind("id", lobby.lobbyId)
                .execute()
        } else throw Exceptions.UserHasNoLobby("Lobby does not exist!")
    }
}