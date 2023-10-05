package com.example.gomoku.http

import com.example.gomoku.http.model.BoardOutputModel
import com.example.gomoku.http.model.GomokuOutputModel
import com.example.gomoku.http.model.GomokuPlayInputModel
import com.example.gomoku.http.model.GomokuStartInputModel
import com.example.gomoku.service.GomokuService
import com.example.gomoku.service.NotFound
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class GamesController (private val gomokuService : GomokuService) {

    //TODO
    @ExceptionHandler(value = [NotFound::class])
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .body("GAME NOT FOUND")

    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGame(@PathVariable id : UUID) :GomokuOutputModel{
        val game = gomokuService.getById(id)
        return GomokuOutputModel(game.id, BoardOutputModel(game.board.toString()), game.playerB.userId, game.playerW.userId)
    }

    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable id: UUID, @RequestBody p: GomokuPlayInputModel): GomokuOutputModel {
        val game = gomokuService.play(id, p.userId, p.c, p.r)
        return GomokuOutputModel(game.id, BoardOutputModel(game.board.toString()), game.playerB.userId, game.playerW.userId)
    }

    @PostMapping(PathTemplate.START)
    fun startGame(@RequestBody s: GomokuStartInputModel): ResponseEntity<GomokuOutputModel> {
        val game = gomokuService.start(s.userIdX, s.userIdO)
        val gameOutModel = GomokuOutputModel(game.id, BoardOutputModel(game.board.toString()), game.playerX, game.playerO)
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(gameOutModel)
    }
}