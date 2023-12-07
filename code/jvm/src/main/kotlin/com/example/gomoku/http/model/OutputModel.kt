package com.example.gomoku.http.model

import com.example.gomoku.domain.*
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
    val boardDim: Int,
    val opening: String,
    val variant: String
) : OutputModel


data class AuthorsOutputModel(val authors: List<Author>) : OutputModel

data class AboutOutputModel(val version: String) : OutputModel

data class LobbiesOutputModel(val lobbyList: List<LobbyOutputModel>) : OutputModel

data class RankingsOutputModel(val rankingList: List<UserStatistics>) : OutputModel

data class UserRankingOutputModel(val userRanking: UserStatistics) : OutputModel

data class MessageOutputModel(val waitMessage: String) : OutputModel

data class ErrorOutputModel(val statusCode: Int, val msg: String?) : OutputModel