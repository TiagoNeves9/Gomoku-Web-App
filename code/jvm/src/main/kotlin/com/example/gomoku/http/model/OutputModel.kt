package com.example.gomoku.http.model

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.Rules
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import com.example.gomoku.domain.board.Board
import com.example.gomoku.domain.board.Cell
import java.net.URI
import java.util.*

interface OutputModel

data class LinkOutputModel(
    private val targetUri: URI,
    private val relation: LinkRelation
) {
    val href = targetUri.toASCIIString()
    val rel = relation.value
}

data class GameOutputModel(
    val id: UUID,
    val userB: User,
    val userW: User,
    val turn: String,
    val boardSize : Int,
    val boardCells: Map<Cell, Turn>,
    val links: List<LinkOutputModel>?
) : OutputModel

data class LobbyOutputModel(
    val lobbyId: UUID,
    val hostUserId: UUID,
    val rules: Rules
) : OutputModel

data class MessageOutputModel(val waitMessage : String) : OutputModel

/*
* Error
* */

data class ErrorOutputModel(val statusCode : Int, val msg : String?) : OutputModel