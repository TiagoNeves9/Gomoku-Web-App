package com.example.gomoku.domain

import java.util.*


/**
Initially (1st phase) we create a lobby with a unique id, a host and a list of rules.
When the other player tries to join the lobby, we create a game and remove the lobby.
Now we will not have the states "selecting rules" or "waiting for other player" or "waiting for start".
 */
class Lobby(
    val lobbyId: UUID,
    val hostUserId: UUID
    //val rules: List<Rule> //TODO: create rules
)
