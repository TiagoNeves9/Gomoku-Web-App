package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.Lobby
import com.example.gomoku.repository.LobbiesRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo

class JdbiLobbiesRepository(private val handle: Handle) : LobbiesRepository {
    override fun insert(lobby: Lobby) : Boolean =
        handle.createUpdate(
            """
                insert into dbo.Lobbies (lobby_id, host) 
                values (:lobby_id, :host)
                """
        )
            .bind("lobby_id", lobby.lobbyId)
            .bind("host", lobby.hostUserId)
            .execute()
            .compareTo(1) == 0

    //Get the unique lobby (if exists)
    override fun getLobby(): Lobby =
        handle.createQuery(
            "select lobby_id, host as hostUserId from dbo.Lobbies"
        )
            .mapTo(Lobby::class.java)
            .first()

    override fun delete(lobby: Lobby) : Boolean =
        handle.createUpdate(
            """
                delete from dbo.Lobbies
                where lobby_id = :lobby_id
                """
        )
            .bind("lobby_id", lobby.lobbyId)
            .execute()
            .compareTo(1) == 0


    override fun doesLobbyExist(): Boolean =
        handle.createQuery("select count(*) from dbo.lobbies")
            .mapTo<Int>()
            .single() == 1

}