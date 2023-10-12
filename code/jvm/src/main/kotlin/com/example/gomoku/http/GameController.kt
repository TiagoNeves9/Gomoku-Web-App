package com.example.gomoku.http

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import com.example.gomoku.domain.board.Piece
import com.example.gomoku.domain.board.createBoard
import com.example.gomoku.http.model.GomokuOutputModel
import com.example.gomoku.http.model.GomokuPlayInputModel
import com.example.gomoku.service.GomokuService
import com.example.gomoku.service.NotFound
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*


@RestController
class GamesController(
    private val gomokuService: GomokuService, private val usersController: UsersController
) {
    //TODO
    @ExceptionHandler(value = [NotFound::class])
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .body("GAME NOT FOUND")

    //TODO("Home page should have a button to go to START page")
    @GetMapping(PathTemplate.HOME)
    fun home() = "Hello Web"

    @GetMapping(PathTemplate.START)
    fun createOrJoinGame(@RequestBody user: User): Game? {
        val lobbyOrNull = gomokuService.createOrJoinLobby()
        return if (lobbyOrNull == null) {
            // create a lobby
            gomokuService.createLobby(user)
            null
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId)
                return null
            // join the unique lobby, start a game and remove the lobby
            gomokuService.deleteLobby(lobbyOrNull)
            val hostUser = usersController.getById(lobbyOrNull.hostUserId)
            val hostPlayer = Player(hostUser, Turn(Piece.BLACK_PIECE))
            //TODO on createGame we are not saving the board
            val game = gomokuService.createGame(hostUser, user)
            /*val game = Game(
                UUID.randomUUID(),
                Pair(hostUser, user),
                createBoard(hostPlayer.second.color),
                hostPlayer,
                0,
                Instant.now()
            )*/
            //GomokuOutputModel(game)
            game
        }
        //val gameOutModel = GomokuWaitingInputModel(game.id, game.playerB.userId, game.playerW.userId)
        //return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(gameOutModel)
    }

    /*
    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGame(@PathVariable id: UUID): GomokuOutputModel {
        val game = gomokuService.getById(id)
        return GomokuOutputModel(
            Game(
                game.gameId,
                game.players,
                game.board,
                game.currentPlayer,
                game.score,
                game.now
            )
        )
    }
    */

    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable id: UUID, @RequestBody p: GomokuPlayInputModel): GomokuOutputModel {
        val game = gomokuService.play(id, p.userId, p.cell)
        return GomokuOutputModel(game)
    }
}