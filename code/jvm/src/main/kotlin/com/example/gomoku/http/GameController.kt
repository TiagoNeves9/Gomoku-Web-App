package com.example.gomoku.http

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.Column
import com.example.gomoku.domain.board.Row
import com.example.gomoku.http.model.CellInputModel
import com.example.gomoku.http.model.GomokuStartInputModel
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
    fun createOrJoinGame(@RequestBody input: GomokuStartInputModel): Game? {
        val user = User(input.userId, input.username, input.encodedPassword)
        val rules =
            Rules(input.boardDim, input.opening.toOpening(), input.variant.toVariant())

        val lobbyOrNull = gomokuService.createOrJoinLobby(rules)
        return if (lobbyOrNull == null) {
            // create a lobby
            gomokuService.createLobby(user, rules)
            null
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId) return null

            // join the unique lobby, start a game and remove the lobby
            gomokuService.deleteLobby(lobbyOrNull)
            val hostUser = usersController.getById(lobbyOrNull.hostUserId)
            Player(hostUser, Turn.BLACK_PIECE)
            return gomokuService.createGame(lobbyOrNull, hostUser, user)
        }
        //val gameOutModel = GomokuWaitingInputModel(game.id, game.playerB.userId, game.playerW.userId)
        //return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(gameOutModel)
    }

    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable("id") gameId: UUID, @RequestBody cell: CellInputModel): Game =
        gomokuService.play(gameId, Cell(Row(cell.row), Column(cell.col)))
}