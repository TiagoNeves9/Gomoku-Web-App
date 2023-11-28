package com.example.gomoku.http.model

import com.example.gomoku.domain.Rules
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import com.example.gomoku.domain.UserStatistics
import com.example.gomoku.domain.board.Cell
import java.net.URI
import java.util.*


interface OutputModel

data class LinkOutputModel(
    private val targetUri: URI,
    private val relation: LinkRelation
) : OutputModel {
    val href: String = targetUri.toASCIIString()
    val rel: String = relation.value
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

data class RankingOutputModel(val rankingList: List<UserStatistics>) : OutputModel

data class MessageOutputModel(val waitMessage: String) : OutputModel

data class ErrorOutputModel(val statusCode: Int, val msg: String?) : OutputModel