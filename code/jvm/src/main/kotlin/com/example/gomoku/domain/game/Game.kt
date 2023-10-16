package com.example.gomoku.domain.game

import com.example.gomoku.domain.User
import com.example.gomoku.domain.game.Board
import com.example.gomoku.domain.board.BoardDraw
import com.example.gomoku.domain.board.BoardWin
import com.example.gomoku.domain.board.Cell
import java.time.Instant
import java.util.*

/*
data class Game(
    val gameId: UUID,
    val users: Pair<User, User>,
    val board: Board,
    val currentPlayer: Player,
    val score: Int,
    val now: Instant
) {

    private fun switchTurn() =
        if (currentPlayer.first == users.first) users.second
        else users.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(board = BoardWin(newBoard.positions, this.currentPlayer))
        else if (newBoard.checkDraw())
            this.copy(board = BoardDraw(newBoard.positions))
        else this.copy(
            board = newBoard,
            currentPlayer = Player(this.switchTurn(), this.currentPlayer.second.other())
        )
    }
    */

data class Game(
    val id: UUID = UUID.randomUUID(),
    var state: GameState = GameState.NEXT_PLAYER_B,
    val board: Board,
    val updated: Instant,
    val playerB: User,
    val playerW: User,
    val score: Int = 0
){
    enum class GameState {
        NEXT_PLAYER_B,
        NEXT_PLAYER_W,
        PLAYER_B_WON,
        PLAYER_W_WON,
        DRAW;

        val finished: Boolean
            get() = this == PLAYER_B_WON || this == PLAYER_W_WON || this == DRAW
    }
    fun switchPlayer() {
        state = if (this.state == GameState.NEXT_PLAYER_W) GameState.NEXT_PLAYER_B
        else GameState.NEXT_PLAYER_W
    }

    fun playerWon() {
        state = if(this.state == GameState.NEXT_PLAYER_B) GameState.PLAYER_B_WON
        else GameState.PLAYER_W_WON
    }

    fun gameDraw(){
        state = GameState.DRAW
    }

    companion object{
        fun create(gameID : UUID?, userB : User, userW : User) : Game {
            return if(gameID != null){
                Game(
                    id = gameID,
                    board = Board.create(15), //todo: change this to receive a board size
                    updated = Instant.now(),
                    playerB = userB,
                    playerW = userW
                )
            } else {
                Game(
                    board = Board.create(15), //todo: change this to receive a board size
                    updated = Instant.now(),
                    playerB = userB,
                    playerW = userW
                )
            }
        }
    }

}