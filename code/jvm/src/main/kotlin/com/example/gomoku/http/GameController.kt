package com.example.gomoku.http

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.Column
import com.example.gomoku.domain.board.Row
import com.example.gomoku.http.model.*
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.GomokuService
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.io.path.Path


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


    @PostMapping(PathTemplate.START)
    fun createOrJoinGame(@RequestBody input: GomokuStartInputModel): SirenModel<OutputModel> {
        val user = User(input.userId, input.username, input.encodedPassword)
        val rules =
            Rules(input.boardDim, input.opening.toOpening(), input.variant.toVariant())

        val lobbyOrNull = gomokuService.createOrJoinLobby(rules)
        if (lobbyOrNull == null) {
            // create a lobby
            val lobby = gomokuService.createLobby(user, rules)
            val lobbyModel = LobbyOutputModel(
                lobby.lobbyId,
                lobby.hostUserId,
                lobby.rules
            )
            return siren(lobbyModel){
                clazz("Lobby")
            }
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId)
                siren(ErrorOutputModel(405, "User can't join his own lobby! ")){}

            // join the unique lobby, start a game and remove the lobby
            gomokuService.deleteLobby(lobbyOrNull)
            val hostUser = usersController.getById(lobbyOrNull.hostUserId)
            Player(hostUser, Turn.BLACK_PIECE)

            val game = gomokuService.createGame(lobbyOrNull, hostUser, user)
            val gameModel = GameOutputModel(
                id = game.gameId,
                userB = game.users.first,
                userW = game.users.second,
                turn = game.currentPlayer.first.username,
                boardSize = game.board.boardSize,
                boardCells = game.board.positions,
                links = null
            )
            return siren(gameModel){
                clazz("Game")
            }
        }
        //val gameOutModel = GomokuWaitingInputModel(game.id, game.playerB.userId, game.playerW.userId)
        //return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(gameOutModel)
    }

    @PostMapping(PathTemplate.PLAY)
    fun play(@PathVariable("id") gameId: UUID, @RequestBody cell: CellInputModel): SirenModel<OutputModel> {
        return try {
            val updatedGame =  gomokuService.play(gameId, Cell(Row(cell.row), Column(cell.col)))
            val gameModel = GameOutputModel(
                id = updatedGame.gameId,
                userB = updatedGame.users.first,
                userW = updatedGame.users.second,
                turn = updatedGame.currentPlayer.first.username,
                boardSize = updatedGame.board.boardSize,
                boardCells = updatedGame.board.positions,
                links = null
            )
            siren(gameModel){
                clazz("Game")
            }
        } catch (ex : Exceptions.GameDoesNotExistException){
            siren(ErrorOutputModel(404,ex.message)){}
        }
    }



    @GetMapping(PathTemplate.IS_GAME_CREATED)
    fun isGameCreated(@PathVariable("id") lobbyId: UUID) : Boolean =
        gomokuService.isGameCreated(lobbyId)


    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGameById(@PathVariable("id") gameId : UUID) : SirenModel<OutputModel> {
        return try {
            val game = gomokuService.getById(gameId)
            val gameModel = GameOutputModel(
                id = game.gameId,
                userB = game.users.first,
                userW = game.users.second,
                turn = game.currentPlayer.first.username,
                boardSize = game.board.boardSize,
                boardCells = game.board.positions,
                links = null
            )
            siren(gameModel){
                clazz("Game")
                action(
                    "play",
                    PathTemplate.play(game.gameId),
                    HttpMethod.POST,
                    "application/x-www-form-urlencoded"
                ){
                    textField("place piece")
                }
            }
        } catch (ex : Exceptions.GameDoesNotExistException){
            siren(ErrorOutputModel(404,ex.message)){}
        }

    }

}