package com.example.gomoku.repository

import com.example.gomoku.domain.Lobby


interface LobbiesRepository {
    fun insert(lobby: Lobby)

    fun getLobby(): Lobby?

    fun delete(lobby: Lobby)
}