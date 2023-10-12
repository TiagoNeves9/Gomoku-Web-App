package com.example.gomoku.http

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.User
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
            // join the unique lobby
            val host = usersController.getById(lobbyOrNull.host)
            gomokuService.createGame(host, user)
            val game = Game(
                UUID.randomUUID(),
                Pair(host, user),
                createBoard(host.color),
                host,
                0,
                Instant.now()
            )
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