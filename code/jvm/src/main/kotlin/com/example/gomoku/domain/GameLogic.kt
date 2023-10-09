package com.example.gomoku.domain

import java.util.UUID


fun play(game: Game, userID: UUID, c: Int, r: Int) =
    game.copy(board = game.board.mutate(if (game.playerB.userId == userID) Cells.BLACK else Cells.WHITE, c, r))