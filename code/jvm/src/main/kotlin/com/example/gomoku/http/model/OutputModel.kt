package com.example.gomoku.http.model

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.Board
import com.example.gomoku.domain.board.Cell
import java.net.URI
import java.util.*


interface OutputModel

data class LinkOutputModel(
    private val targetUri: URI,
    private val relation: LinkRelation
) : OutputModel {
    val href = targetUri.toASCIIString()
    val rel = relation.value
}

data class GameOutputModel(
    val id: UUID,
    val userB: User,
    val userW: User,
    val turn: String,
    val rules: Rules,
    val boardCells: Map<Cell, Turn>,
    val boardState: String
) : OutputModel

data class LobbyOutputModel(
    val lobbyId: UUID,
    val hostUserId: UUID,
    val rules: Rules
) : OutputModel

data class RankingOutputModel(
    val rankingList: List<UserStatistics>
) : OutputModel

data class MessageOutputModel(val waitMessage: String) : OutputModel

/*
* Error
* */

data class ErrorOutputModel(val statusCode: Int, val msg: String?) : OutputModel