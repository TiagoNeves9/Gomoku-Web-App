package com.example.gomoku.domain.game

data class Cell(
    private var state : CellState,
    val col : Int,
    val row : Int
) {
    enum class CellState(var c: Char) {
        EMPTY('+'),
        WHITE('W'),
        BLACK('B');

    }

    fun getState() : CellState {
        return state
    }

    fun updateStateToBlackStone() {
        state = CellState.BLACK
    }

    fun updateStateToWhiteStone() {
        state = CellState.WHITE
    }

    fun updateStateToEmptyCell(){
        state = CellState.EMPTY
    }

    companion object {
        fun fromChar(c: Char) = when (c) {
            '+' -> CellState.EMPTY
            'W' -> CellState.WHITE
            'B' -> CellState.BLACK
            else -> throw IllegalArgumentException("Invalid value for Cell.CellState")
        }
    }

}