package com.example.gomoku.http

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.toCell
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.GomokuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class GamesController(
    private val gomokuService: GomokuService,
    private val usersController: UsersController
) {
    //TODO
    @ExceptionHandler(value = [Exceptions.NotFound::class])
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .body("GAME NOT FOUND")

    //TODO("Home page should have a button to go to START page")
    @GetMapping(PathTemplate.HOME)
    fun home() = "Hello Web"

    @PostMapping(PathTemplate.START)
    fun createOrJoinGame(@RequestBody user: User): Game? {
        val lobbyOrNull = gomokuService.createOrJoinLobby()
        return if (lobbyOrNull == null) {
            // create a lobby
            gomokuService.createLobby(user)
            null
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId) return null

            // join the unique lobby, start a game and remove the lobby
            gomokuService.deleteLobby(lobbyOrNull)
            val hostUser = usersController.getById(lobbyOrNull.hostUserId)
            Player(hostUser, Turn.BLACK_PIECE)
            return gomokuService.createGame(hostUser, user)
        }
        //val gameOutModel = GomokuWaitingInputModel(game.id, game.playerB.userId, game.playerW.userId)
        //return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(gameOutModel)
    }

    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable("id") gameId: UUID, @RequestBody cellStr: String): Game {
        val cell = cellStr.toCell()
        val game = gomokuService.play(gameId, cell)
        return game
    }
}