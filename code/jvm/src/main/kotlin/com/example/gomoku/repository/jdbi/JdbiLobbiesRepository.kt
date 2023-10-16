package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Lobby
import com.example.gomoku.repository.LobbiesRepository
import org.jdbi.v3.core.Handle


class JdbiLobbiesRepository(private val handle: Handle) : LobbiesRepository {
    override fun insert(lobby: Lobby) {
        handle.createUpdate(
            """
                insert into dbo.Lobbies (lobby_id, host) 
                values (:lobby_id, :host)
                """
        )
            .bind("lobby_id", lobby.lobbyId)
            .bind("host", lobby.hostUserId)
            .execute()
    }

    //Get the unique lobby (if exists)
    override fun getLobby(): Lobby? =
        handle.createQuery(
            "select lobby_id, host as hostUserId from dbo.Lobbies"
        )
            .mapTo(Lobby::class.java)
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