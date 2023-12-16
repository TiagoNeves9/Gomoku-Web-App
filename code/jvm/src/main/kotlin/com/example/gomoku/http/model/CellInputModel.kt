package com.example.gomoku.http.model

import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.toCell


data class CellInputModel(val row: Int, val col: Char, val boardSize: Int) {
    fun toCell(): Cell {
        val r = row.toString()
        val str = r + col
        val cell = str.toCell(boardSize)
        //println(cell)
        return cell
    }
}