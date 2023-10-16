package com.example.gomoku.http

import com.example.gomoku.domain.game.Game
import com.example.gomoku.http.model.*
import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn
import com.example.gomoku.domain.User
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.GomokuService
import com.example.gomoku.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class GamesController(private val gomokuService: GomokuService, private val userService: UserService) {

    @ExceptionHandler(value = [Exceptions.NotFound::class])
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .body("GAME NOT FOUND")

    /*
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
            val hostPlayer = Player(hostUser, Turn.BLACK_PIECE)
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
    */

    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGame(@PathVariable("id") id: UUID): GameOutputModel {
        val game = gomokuService.getById(id)
        return GameOutputModel(
            game.id,
            BoardOutputModel(game.board.toString()),
            game.playerB.userId,
            game.playerW.userId
        )
    }

    /*@PostMapping(PathTemplate.CREATE_GAME)
    fun createGame(@RequestBody gameSetup: GameSetupInputModel) : Game {
        return gomokuService.createGame(gameSetup.usernamePB, gameSetup.usernamePW)
    } */

    @GetMapping(PathTemplate.IS_GAME_CREATED)
    fun isGameCreated(@PathVariable("id") gameId: UUID) : Boolean {
        return gomokuService.isGameCreated(gameId)
    }



    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable("id") id: UUID, @RequestBody play: GamePlayInputModel): GameOutputModel {
        val updatedGame = gomokuService.makePlay(id, play.userId, play.c, play.r)
        return GameOutputModel(
            updatedGame.id,
            BoardOutputModel(updatedGame.board.toString()),
            updatedGame.playerB.userId,
            updatedGame.playerW.userId
        )
    }

    //<-

    /*
    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable id: UUID, @RequestBody p: GomokuPlayInputModel): GomokuOutputModel {
        val game = gomokuService.play(id, p.userId, p.cell)
        return GomokuOutputModel(game)
    }

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
            val hostPlayer = Player(hostUser, Turn.BLACK_PIECE)
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
    */

}