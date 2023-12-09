package com.example.gomoku.repository

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.Rules
import com.example.gomoku.http.model.LobbyOutputModel
import java.util.*


interface LobbiesRepository {
    fun insert(lobby: Lobby)

    fun getLobby(rules: Rules): LobbyOutputModel?

    fun getAll(): List<LobbyOutputModel>

    fun delete(lobby: LobbyOutputModel): Int

    fun deleteUserLobby(userId: UUID): Int
}