package com.example.gomoku.repository.jdbi.mappers

import com.example.gomoku.domain.game.Board
import com.example.gomoku.repository.jdbi.JdbiGamesRepository
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class BoardMapper: ColumnMapper<Board> {
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): Board {
        return JdbiGamesRepository.deserializeBoardFromJson(r.getString(columnNumber))
    }
}