package com.example.gomoku.domain

import java.time.Instant
import java.util.*


/**
Initially (1st phase) we create a lobby with a unique id, a room name, a host and a list of rules.
When the other player tries to join the lobby, we create a game and remove the lobby.
Now we will not have the states "selecting rules" or "waiting for other player" or "waiting for start".
 */
class Lobby private constructor(
    id: UUID,
    private val host: User,
    //val rules: List<Rule> //TODO: create rules
) {
    fun createLobby(user: User) {
        val newLobby = Lobby(UUID.randomUUID(), user)
    }

    fun joinLobby(user: User): Game =
        Game(
            UUID.randomUUID(),
            Game.GameState.NEXT_PLAYER_B,
            Board.create(),
            Instant.now(),
            this.host,
            user
        )
}