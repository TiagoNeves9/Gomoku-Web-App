package com.example.gomoku.repository

import com.example.gomoku.domain.Lobby

interface LobbiesRepository {
    fun insert(lobby: Lobby) : Boolean

    fun getLobby(): Lobby

    fun delete(lobby: Lobby) : Boolean
    fun doesLobbyExist() : Boolean
}