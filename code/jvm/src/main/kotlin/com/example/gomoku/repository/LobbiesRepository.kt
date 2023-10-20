package com.example.gomoku.repository

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.Rules


interface LobbiesRepository {
    fun insert(lobby: Lobby)

    fun getLobby(rules: Rules): Lobby?

    fun getAll(): List<Lobby>

    fun delete(lobby: Lobby)
}