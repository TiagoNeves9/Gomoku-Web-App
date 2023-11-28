package com.example.gomoku.http

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.*
import com.example.gomoku.http.model.*
import com.example.gomoku.http.pipeline.Authenticated
import com.example.gomoku.http.pipeline.AuthenticatedUserArgumentResolver
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.GomokuService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class GamesController(
    private val gomokuService: GomokuService,
    private val usersController: UsersController
) {
    @ExceptionHandler(value = [Exceptions.NotFoundException::class])
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .body("GAME NOT FOUND")

    @Authenticated
    @PostMapping(PathTemplate.START)
    fun createOrJoinGame(
        request: HttpServletRequest,
        @RequestBody input: GomokuStartInputModel
    ): SirenModel<OutputModel> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return siren(ErrorOutputModel(401, "User not authenticated! ")) {}

        check(input.boardDim == BOARD_DIM || input.boardDim == BIG_BOARD_DIM) {
            "Board dimension must be $BOARD_DIM or $BIG_BOARD_DIM!"
        }

        val user = User(input.userId, input.username, input.encodedPassword)
        val rules = Rules(input.boardDim, input.opening.toOpening(), input.variant.toVariant())

        val lobbyOrNull = gomokuService.createOrJoinLobby(rules)
        if (lobbyOrNull == null) {
            // create a lobby
            val lobby = gomokuService.createLobby(user, rules)
            val lobbyModel = LobbyOutputModel(
                lobby.lobbyId,
                lobby.hostUserId,
                lobby.rules
            )
            return siren(lobbyModel) {
                clazz("Lobby")
            }
        } else {
            // host user is trying to join the lobby
            if (lobbyOrNull.hostUserId == user.userId)
                siren(ErrorOutputModel(405, "User can't join his own lobby!")) {}

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
                rules = game.rules,
                boardCells = game.board.positions,
                boardState = game.board.typeToString()
            )
            return siren(gameModel) { clazz("Game") }
        }
    }

    @GetMapping(PathTemplate.LOBBIES)
    fun getLobbies(): List<Lobby> = gomokuService.getLobbies()

    @GetMapping(PathTemplate.GAMES)
    fun getGames(): List<Game> = gomokuService.getGames()

    @Authenticated
    @PostMapping(PathTemplate.PLAY)
    fun play(
        request: HttpServletRequest,
        @PathVariable("id") gameId: UUID,
        @RequestBody cell: CellInputModel
    ): SirenModel<OutputModel> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return siren(ErrorOutputModel(401, "User not authenticated! ")) {}

        return try {
            val updatedGame = gomokuService.play(gameId, Cell(Row(cell.row), Column(cell.col)))
            val gameModel = GameOutputModel(
                id = updatedGame.gameId,
                userB = updatedGame.users.first,
                userW = updatedGame.users.second,
                turn = updatedGame.currentPlayer.first.username,
                rules = updatedGame.rules,
                boardCells = updatedGame.board.positions,
                boardState = updatedGame.board.typeToString()
            )
            siren(gameModel) { clazz("Game") }
        } catch (ex: Exceptions.GameDoesNotExistException) {
            siren(ErrorOutputModel(404, ex.message)) {}
        } catch (ex: Exceptions.PlayNotAllowedException) {
            siren(ErrorOutputModel(405, ex.message)) {}
        } catch (ex: Exceptions.WrongPlayException) {
            siren(ErrorOutputModel(405, ex.message)) {}
        } catch (ex: Exception) {
            siren(ErrorOutputModel(400, ex.message)) {}
        }
    }

    @GetMapping(PathTemplate.SPECTATE)
    fun spectate(@PathVariable("id") gameId: UUID): SirenModel<OutputModel> {
        return try {
            if (gomokuService.isGameCreated(gameId)) {
                val game = gomokuService.getById(gameId)
                val gameModel = GameOutputModel(
                    id = game.gameId,
                    userB = game.users.first,
                    userW = game.users.second,
                    turn = game.currentPlayer.first.username,
                    rules = game.rules,
                    boardCells = game.board.positions,
                    boardState = game.board.typeToString()
                )
                siren(gameModel) { clazz("Game") }
            } else siren(ErrorOutputModel(404, "Game was not been created! ")) {}
        } catch (ex: Exceptions.GameDoesNotExistException) {
            siren(ErrorOutputModel(404, "Game was not been created! ")) {}
        }
    }

    @Authenticated
    @GetMapping(PathTemplate.IS_GAME_CREATED)
    fun isGameCreated(
        request: HttpServletRequest,
        @PathVariable("id") lobbyId: UUID
    ): SirenModel<OutputModel> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return siren(ErrorOutputModel(401, "User not authenticated! ")) {}

        return try {
            if (gomokuService.isGameCreated(lobbyId)) {
                siren(MessageOutputModel("Game has been created! ")) {
                    clazz("Check if game is created! ")
                    action(
                        "game",
                        PathTemplate.gameById(lobbyId),
                        HttpMethod.GET,
                        "application/x-www-form-urlencoded"
                    ) {
                        textField("get game")
                    }
                }
            } else siren(MessageOutputModel("Waiting for another player to join! ")) {
                clazz("Check if game is created! ")
                link(PathTemplate.home(), LinkRelations.HOME)
            }
        } catch (ex: Exception) {
            siren(ErrorOutputModel(404, "Game has not been created! ")) {}
        }
    }

    @Authenticated
    @GetMapping(PathTemplate.GAME_BY_ID)
    fun getGameById(
        request: HttpServletRequest,
        @PathVariable("id") gameId: UUID
    ): SirenModel<OutputModel> {
        request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return siren(ErrorOutputModel(401, "User not authenticated! ")) {}
        return try {
            val game = gomokuService.getById(gameId)
            val gameModel = GameOutputModel(
                id = game.gameId,
                userB = game.users.first,
                userW = game.users.second,
                turn = game.currentPlayer.first.username,
                rules = game.rules,
                boardCells = game.board.positions,
                boardState = game.board.typeToString()
            )
            siren(gameModel) {
                clazz("Game")
                action(
                    "play",
                    PathTemplate.play(game.gameId),
                    HttpMethod.POST,
                    "application/x-www-form-urlencoded"
                ) {
                    textField("place piece")
                }
            }
        } catch (ex: Exceptions.GameDoesNotExistException) {
            siren(ErrorOutputModel(404, ex.message)) {}
        }
    }
}